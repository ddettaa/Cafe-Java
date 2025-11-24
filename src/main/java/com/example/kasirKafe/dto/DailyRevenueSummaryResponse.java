package com.example.kasirKafe.dto;

import java.math.BigDecimal;
import java.util.List;

public class DailyRevenueSummaryResponse {
    private BigDecimal totalRevenue;
    private Long totalOrders;
    private BigDecimal averageOrderValue;
    private BigDecimal cashRevenue;
    private BigDecimal qrisRevenue;
    private List<DailyRevenueResponse> dailyData;

    public DailyRevenueSummaryResponse() {
    }

    public DailyRevenueSummaryResponse(BigDecimal totalRevenue,
                                       Long totalOrders,
                                       BigDecimal averageOrderValue,
                                       BigDecimal cashRevenue,
                                       BigDecimal qrisRevenue,
                                       List<DailyRevenueResponse> dailyData) {
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
        this.totalOrders = totalOrders != null ? totalOrders : 0L;
        this.averageOrderValue = averageOrderValue != null ? averageOrderValue : BigDecimal.ZERO;
        this.cashRevenue = cashRevenue != null ? cashRevenue : BigDecimal.ZERO;
        this.qrisRevenue = qrisRevenue != null ? qrisRevenue : BigDecimal.ZERO;
        this.dailyData = dailyData;
    }

    // Getters and Setters
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders != null ? totalOrders : 0L;
    }

    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue != null ? averageOrderValue : BigDecimal.ZERO;
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

    public List<DailyRevenueResponse> getDailyData() {
        return dailyData;
    }

    public void setDailyData(List<DailyRevenueResponse> dailyData) {
        this.dailyData = dailyData;
    }
}

