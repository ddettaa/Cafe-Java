package com.example.kasirKafe.service;

import com.example.kasirKafe.dto.DailyRevenueResponse;
import com.example.kasirKafe.dto.DailyRevenueSummaryResponse;
import com.example.kasirKafe.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RevenueService {

    private final PaymentRepository paymentRepository;

    public RevenueService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional(readOnly = true)
    public DailyRevenueSummaryResponse getDailyRevenueSummary(LocalDate fromDate, LocalDate toDate) {
        // Set default to last 30 days if not provided
        if (fromDate == null) {
            fromDate = LocalDate.now().minusDays(30);
        }
        if (toDate == null) {
            toDate = LocalDate.now();
        }

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);

        // Get daily revenue data
        List<Object[]> dailyRevenueData = paymentRepository.getDailyRevenue(fromDateTime, toDateTime);

        // Convert to DTOs
        List<DailyRevenueResponse> dailyData = dailyRevenueData.stream()
                .map(row -> {
                    // Handle java.sql.Date from native query
                    LocalDate date;
                    if (row[0] instanceof java.sql.Date) {
                        date = ((java.sql.Date) row[0]).toLocalDate();
                    } else if (row[0] instanceof java.util.Date) {
                        date = ((java.util.Date) row[0]).toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate();
                    } else {
                        date = (LocalDate) row[0];
                    }
                    
                    BigDecimal totalRevenue = (BigDecimal) row[1];
                    Long orderCount = ((Number) row[2]).longValue();
                    BigDecimal cashRevenue = (BigDecimal) row[3];
                    BigDecimal qrisRevenue = (BigDecimal) row[4];
                    Long cashOrderCount = ((Number) row[5]).longValue();
                    Long qrisOrderCount = ((Number) row[6]).longValue();

                    return new DailyRevenueResponse(
                            date,
                            totalRevenue,
                            orderCount,
                            cashRevenue,
                            qrisRevenue,
                            cashOrderCount,
                            qrisOrderCount
                    );
                })
                .collect(Collectors.toList());

        // Fill in missing dates with zero revenue
        List<DailyRevenueResponse> completeDailyData = fillMissingDates(fromDate, toDate, dailyData);

        // Calculate summary totals
        BigDecimal totalRevenue = paymentRepository.getTotalRevenue(fromDateTime, toDateTime);
        BigDecimal cashRevenue = paymentRepository.getRevenueByPaymentType(fromDateTime, toDateTime, "CASH");
        BigDecimal qrisRevenue = paymentRepository.getRevenueByPaymentType(fromDateTime, toDateTime, "QRIS");

        Long totalOrders = completeDailyData.stream()
                .mapToLong(DailyRevenueResponse::getOrderCount)
                .sum();

        BigDecimal averageOrderValue = BigDecimal.ZERO;
        if (totalOrders > 0 && totalRevenue != null) {
            averageOrderValue = totalRevenue.divide(
                    BigDecimal.valueOf(totalOrders),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        return new DailyRevenueSummaryResponse(
                totalRevenue,
                totalOrders,
                averageOrderValue,
                cashRevenue,
                qrisRevenue,
                completeDailyData
        );
    }

    @Transactional(readOnly = true)
    public List<DailyRevenueResponse> getDailyRevenue(LocalDate fromDate, LocalDate toDate) {
        // Set default to last 30 days if not provided
        if (fromDate == null) {
            fromDate = LocalDate.now().minusDays(30);
        }
        if (toDate == null) {
            toDate = LocalDate.now();
        }

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);

        List<Object[]> dailyRevenueData = paymentRepository.getDailyRevenue(fromDateTime, toDateTime);

        List<DailyRevenueResponse> dailyData = dailyRevenueData.stream()
                .map(row -> {
                    // Handle java.sql.Date from native query
                    LocalDate date;
                    if (row[0] instanceof java.sql.Date) {
                        date = ((java.sql.Date) row[0]).toLocalDate();
                    } else if (row[0] instanceof java.util.Date) {
                        date = ((java.util.Date) row[0]).toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate();
                    } else {
                        date = (LocalDate) row[0];
                    }
                    
                    BigDecimal totalRevenue = (BigDecimal) row[1];
                    Long orderCount = ((Number) row[2]).longValue();
                    BigDecimal cashRevenue = (BigDecimal) row[3];
                    BigDecimal qrisRevenue = (BigDecimal) row[4];
                    Long cashOrderCount = ((Number) row[5]).longValue();
                    Long qrisOrderCount = ((Number) row[6]).longValue();

                    return new DailyRevenueResponse(
                            date,
                            totalRevenue,
                            orderCount,
                            cashRevenue,
                            qrisRevenue,
                            cashOrderCount,
                            qrisOrderCount
                    );
                })
                .collect(Collectors.toList());

        // Fill in missing dates with zero revenue
        return fillMissingDates(fromDate, toDate, dailyData);
    }

    /**
     * Fill in missing dates with zero revenue to ensure complete data for charts
     */
    private List<DailyRevenueResponse> fillMissingDates(
            LocalDate fromDate,
            LocalDate toDate,
            List<DailyRevenueResponse> existingData
    ) {
        Map<LocalDate, DailyRevenueResponse> dataMap = existingData.stream()
                .collect(Collectors.toMap(
                        DailyRevenueResponse::getDate,
                        d -> d,
                        (d1, d2) -> d1
                ));

        List<DailyRevenueResponse> completeData = new ArrayList<>();
        LocalDate currentDate = fromDate;

        while (!currentDate.isAfter(toDate)) {
            DailyRevenueResponse dailyData = dataMap.get(currentDate);
            if (dailyData == null) {
                // Create zero revenue entry for missing date
                dailyData = new DailyRevenueResponse(
                        currentDate,
                        BigDecimal.ZERO,
                        0L,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        0L,
                        0L
                );
            }
            completeData.add(dailyData);
            currentDate = currentDate.plusDays(1);
        }

        return completeData;
    }
}

