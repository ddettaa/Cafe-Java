package com.example.kasirKafe.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyRevenueResponse {
    private LocalDate date;
    private BigDecimal totalRevenue;
    private Long orderCount;
    private BigDecimal cashRevenue;
    private BigDecimal qrisRevenue;
    private Long cashOrderCount;
    private Long qrisOrderCount;

    public DailyRevenueResponse() {
    }

    public DailyRevenueResponse(LocalDate date,
                                BigDecimal totalRevenue,
                                Long orderCount,
                                BigDecimal cashRevenue,
                                BigDecimal qrisRevenue,
                                Long cashOrderCount,
                                Long qrisOrderCount) {
        this.date = date;
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
        this.orderCount = orderCount != null ? orderCount : 0L;
        this.cashRevenue = cashRevenue != null ? cashRevenue : BigDecimal.ZERO;
        this.qrisRevenue = qrisRevenue != null ? qrisRevenue : BigDecimal.ZERO;
        this.cashOrderCount = cashOrderCount != null ? cashOrderCount : 0L;
        this.qrisOrderCount = qrisOrderCount != null ? qrisOrderCount : 0L;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount != null ? orderCount : 0L;
    }

    public BigDecimal getCashRevenue() {
        return cashRevenue;
    }

    public void setCashRevenue(BigDecimal cashRevenue) {
        this.cashRevenue = cashRevenue != null ? cashRevenue : BigDecimal.ZERO;
    }

    public BigDecimal getQrisRevenue() {
        return qrisRevenue;
    }

    public void setQrisRevenue(BigDecimal qrisRevenue) {
        this.qrisRevenue = qrisRevenue != null ? qrisRevenue : BigDecimal.ZERO;
    }

    public Long getCashOrderCount() {
        return cashOrderCount;
    }

    public void setCashOrderCount(Long cashOrderCount) {
        this.cashOrderCount = cashOrderCount != null ? cashOrderCount : 0L;
    }

    public Long getQrisOrderCount() {
        return qrisOrderCount;
    }

    public void setQrisOrderCount(Long qrisOrderCount) {
        this.qrisOrderCount = qrisOrderCount != null ? qrisOrderCount : 0L;
    }
}

