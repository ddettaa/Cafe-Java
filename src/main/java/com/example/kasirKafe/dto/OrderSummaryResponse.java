package com.example.kasirKafe.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSummaryResponse {

    private Long id;
    private String orderCode;
    private String tableName;
    private String status;
    private LocalDateTime orderTime;
    private BigDecimal totalAmount;

    public OrderSummaryResponse() {
    }

    public OrderSummaryResponse(Long id,
                                String orderCode,
                                String tableName,
                                String status,
                                LocalDateTime orderTime,
                                BigDecimal totalAmount) {
        this.id = id;
        this.orderCode = orderCode;
        this.tableName = tableName;
        this.status = status;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getTableName() {
        return tableName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
