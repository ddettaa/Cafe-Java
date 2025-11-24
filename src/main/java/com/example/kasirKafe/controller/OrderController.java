package com.example.kasirKafe.controller;

import com.example.kasirKafe.dto.AddOrderItemRequest;
import com.example.kasirKafe.dto.CheckoutRequest;
import com.example.kasirKafe.dto.CreateOrderRequest;
import com.example.kasirKafe.model.OrderHeader;
import com.example.kasirKafe.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.kasirKafe.dto.OrderSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
        // LIST ORDERS dengan pagination + filter
    // contoh: /api/orders?status=PAID&from=2025-01-01&to=2025-01-31&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<OrderSummaryResponse>> listOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<OrderSummaryResponse> result = service.searchOrders(status, from, to, page, size);
        return ResponseEntity.ok(result);
    }


    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // CREATE ORDER
    @PostMapping
    public ResponseEntity<OrderHeader> createOrder(@RequestBody CreateOrderRequest req) {
        return ResponseEntity.ok(service.createOrder(req));
    }

    // GET ORDER
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderHeader> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.getOrder(orderId));
    }

    // ADD ITEM (alternative endpoint)
    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderHeader> addItemAlt(
            @PathVariable Long orderId,
            @RequestBody AddOrderItemRequest req
    ) {
        return ResponseEntity.ok(service.addItem(orderId, req));
    }

    // ADD ITEM
    @PostMapping("/{orderId}/add-item")
    public ResponseEntity<OrderHeader> addItem(
            @PathVariable Long orderId,
            @RequestBody AddOrderItemRequest req
    ) {
        return ResponseEntity.ok(service.addItem(orderId, req));
    }

    // CHECKOUT
    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<OrderHeader> checkout(
            @PathVariable Long orderId,
            @RequestBody CheckoutRequest req
    ) {
        return ResponseEntity.ok(service.checkout(orderId, req));
    }
}
