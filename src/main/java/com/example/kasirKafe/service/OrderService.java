package com.example.kasirKafe.service;

import com.example.kasirKafe.dto.AddOrderItemRequest;
import com.example.kasirKafe.dto.CheckoutRequest;
import com.example.kasirKafe.dto.CreateOrderRequest;

import com.example.kasirKafe.model.*;
import com.example.kasirKafe.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.kasirKafe.dto.OrderSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.time.LocalTime;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderHeaderRepository orderRepo;
    private final OrderItemRepository itemRepo;
    private final MenuItemRepository menuRepo;
    private final TableKafeRepository tableRepo;
    private final PaymentRepository paymentRepo;
    private final MenuItemIngredientRepository menuItemIngredientRepo;
    private final StockMovementRepository stockMovementRepo;


    public OrderService(OrderHeaderRepository orderRepo,
                        OrderItemRepository itemRepo,
                        MenuItemRepository menuRepo,
                        TableKafeRepository tableRepo,
                        PaymentRepository paymentRepo,
                        MenuItemIngredientRepository menuItemIngredientRepo,
                        StockMovementRepository stockMovementRepo) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.menuRepo = menuRepo;
        this.tableRepo = tableRepo;
        this.paymentRepo = paymentRepo;
        this.menuItemIngredientRepo = menuItemIngredientRepo;
        this.stockMovementRepo = stockMovementRepo;
    }

    @Transactional
    public OrderHeader createOrder(CreateOrderRequest req) {
        TableKafe table = tableRepo.findById(req.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found with id " + req.getTableId()));

        table.setStatus("OCCUPIED");
        tableRepo.save(table);

        OrderHeader order = new OrderHeader();
        order.setOrderCode("ORD-" + System.currentTimeMillis());
        order.setTable(table);
        order.setOrderStatus("DRAFT");
        order.setOrderTime(LocalDateTime.now());
        order.setSubtotalAmount(BigDecimal.ZERO);
        order.setTaxAmount(BigDecimal.ZERO);
        order.setTotalAmount(BigDecimal.ZERO);

        return orderRepo.save(order);
    }

    @Transactional
    public OrderHeader addItem(Long orderId, AddOrderItemRequest req) {
        OrderHeader order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

        MenuItem menuItem = menuRepo.findById(req.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found with id " + req.getMenuItemId()));

        int nextLine = itemRepo.countByOrder_Id(orderId) + 1;

        OrderItem item = new OrderItem();
        OrderItemId id = new OrderItemId(orderId, nextLine);
        item.setId(id);
        item.setOrder(order);
        item.setMenuItem(menuItem);
        item.setQuantity(req.getQuantity());
        item.setUnitPriceAmount(menuItem.getPriceAmount());
        item.setLineTotalAmount(menuItem.getPriceAmount()
                .multiply(BigDecimal.valueOf(req.getQuantity())));

        itemRepo.save(item);

        recalcTotals(orderId);

        return orderRepo.findById(orderId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public OrderHeader getOrder(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
    }

    @Transactional
    public OrderHeader checkout(Long orderId, CheckoutRequest req) {
        OrderHeader order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

        if (!"DRAFT".equals(order.getOrderStatus()) && !"ONGOING".equals(order.getOrderStatus())) {
            throw new RuntimeException("Order status must be DRAFT/ONGOING to checkout");
        }

        recalcTotals(orderId);
        
        // Refresh order after recalcTotals
        order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

        if (order.getTotalAmount() == null) {
            throw new RuntimeException("Order has no total amount");
        }

        if (req.getPaidAmount() == null) {
            throw new RuntimeException("Paid amount is required");
        }

        if (req.getPaidAmount().compareTo(order.getTotalAmount()) < 0) {
            throw new RuntimeException("Paid amount is less than total");
        }

        Payment payment;
        if ("CASH".equalsIgnoreCase(req.getPaymentType())) {
            if (req.getCashReceived() == null) {
                throw new RuntimeException("cashReceived is required for CASH payment");
            }
            
            CashPayment cash = new CashPayment();
            cash.setPaymentType("CASH");
            cash.setPaidAmount(order.getTotalAmount());
            cash.setPaymentTime(LocalDateTime.now());
            cash.setOrder(order);
            cash.setCashReceived(req.getCashReceived());
            cash.setChangeAmount(req.getCashReceived().subtract(order.getTotalAmount()));

            payment = paymentRepo.save(cash);

        } else if ("QRIS".equalsIgnoreCase(req.getPaymentType())) {
            if (req.getQrisReference() == null || req.getQrisReference().trim().isEmpty()) {
                throw new RuntimeException("qrisReference is required for QRIS payment");
            }
            if (req.getIssuer() == null || req.getIssuer().trim().isEmpty()) {
                throw new RuntimeException("issuer is required for QRIS payment");
            }
            
            QrisPayment qris = new QrisPayment();
            qris.setPaymentType("QRIS");
            qris.setPaidAmount(order.getTotalAmount());
            qris.setPaymentTime(LocalDateTime.now());
            qris.setOrder(order);
            qris.setQrisReference(req.getQrisReference().trim());
            qris.setIssuer(req.getIssuer().trim());

            payment = paymentRepo.save(qris);

        } else {
            throw new RuntimeException("Unsupported payment type: " + req.getPaymentType());
        }

        // Refresh order to ensure it's managed
        order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        
        order.setOrderStatus("PAID");
        order.setPayment(payment);
        orderRepo.save(order);

        TableKafe table = order.getTable();
        table.setStatus("AVAILABLE");
        tableRepo.save(table);

        // Generate stock out movement for order items
        generateStockOutForOrder(order);

        return order;
    }

    @Transactional
    protected void generateStockOutForOrder(OrderHeader order) {
        // ambil semua item di order
        List<OrderItem> items = itemRepo.findByOrder_Id(order.getId());

        for (OrderItem oi : items) {
            Long menuId = oi.getMenuItem().getId();
            Integer qty = oi.getQuantity();

            // ambil resep / komposisi untuk menu ini
            var recipeList = menuItemIngredientRepo.findByMenuItem_Id(menuId);

            for (var recipe : recipeList) {
                var ingredient = recipe.getIngredient();
                // jumlah total = quantity per porsi * jumlah porsi
                var totalQty = recipe.getQuantity()
                        .multiply(BigDecimal.valueOf(qty));

                StockMovement sm = new StockMovement();
                sm.setIngredient(ingredient);
                sm.setMovementType("OUT");
                sm.setQuantity(totalQty);
                sm.setUnit(ingredient.getUnit());
                sm.setRefOrder(order);
                sm.setNote("Consumption for order " + order.getOrderCode());
                sm.setMovementTime(LocalDateTime.now());

                stockMovementRepo.save(sm);
            }
        }
    }

    @Transactional
    protected void recalcTotals(Long orderId) {
        OrderHeader order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

        List<OrderItem> items = itemRepo.findByOrder_Id(orderId);

        BigDecimal subtotal = items.stream()
                .map(OrderItem::getLineTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tax = subtotal.multiply(new BigDecimal("0.10"));
        BigDecimal total = subtotal.add(tax);

        order.setSubtotalAmount(subtotal);
        order.setTaxAmount(tax);
        order.setTotalAmount(total);

        orderRepo.save(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderSummaryResponse> searchOrders(
            String status,
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size
    ) {
        // normalisasi status: kosong string -> null
        String normalizedStatus = (status != null && !status.isBlank()) ? status : null;

        // convert LocalDate ke LocalDateTime (awal hari & akhir hari)
        LocalDateTime from = null;
        LocalDateTime to = null;

        if (fromDate != null) {
            from = fromDate.atStartOfDay();
        }
        if (toDate != null) {
            // akhir hari: 23:59:59.999...
            to = toDate.atTime(LocalTime.MAX);
        }

        PageRequest pageable = PageRequest.of(page, size);

        Page<OrderHeader> ordersPage = orderRepo.searchOrders(
                normalizedStatus,
                from,
                to,
                pageable
        );

        // mapping ke DTO
        return ordersPage.map(this::toSummaryDto);
    }

    private OrderSummaryResponse toSummaryDto(OrderHeader o) {
        String tableName = (o.getTable() != null) ? o.getTable().getName() : null;
        return new OrderSummaryResponse(
                o.getId(),
                o.getOrderCode(),
                tableName,
                o.getOrderStatus(),
                o.getOrderTime(),
                o.getTotalAmount()
        );
    }

}
