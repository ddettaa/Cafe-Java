package com.example.kasirKafe.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_header")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", unique = true, nullable = false, length = 30)
    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableKafe table;

    @Column(name = "order_status", length = 20, nullable = false)
    private String orderStatus; // DRAFT, ONGOING, PAID, CANCELLED

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "subtotal_amount", precision = 15, scale = 2)
    private BigDecimal subtotalAmount;

    @Column(name = "subtotal_currency", length = 3)
    private String subtotalCurrency = "IDR";

    @Column(name = "tax_amount", precision = 15, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "tax_currency", length = 3)
    private String taxCurrency = "IDR";

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "total_currency", length = 3)
    private String totalCurrency = "IDR";

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Payment payment;

    // getters & setters

    public Long getId() {
        return id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public TableKafe getTable() {
        return table;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }

    public String getSubtotalCurrency() {
        return subtotalCurrency;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public String getTaxCurrency() {
        return taxCurrency;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getTotalCurrency() {
        return totalCurrency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setTable(TableKafe table) {
        this.table = table;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public void setSubtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public void setSubtotalCurrency(String subtotalCurrency) {
        this.subtotalCurrency = subtotalCurrency;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setTaxCurrency(String taxCurrency) {
        this.taxCurrency = taxCurrency;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalCurrency(String totalCurrency) {
        this.totalCurrency = totalCurrency;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
