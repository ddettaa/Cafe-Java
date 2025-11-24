package com.example.kasirKafe.controller;

import com.example.kasirKafe.dto.DailyRevenueResponse;
import com.example.kasirKafe.dto.DailyRevenueSummaryResponse;
import com.example.kasirKafe.service.RevenueService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/revenue")
@CrossOrigin(origins = "*")
public class RevenueController {

    private final RevenueService revenueService;

    public RevenueController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    /**
     * Get daily revenue summary with chart data
     * Example: /api/revenue/daily?from=2025-01-01&to=2025-01-31
     * If from/to not provided, defaults to last 30 days
     */
    @GetMapping("/daily")
    public ResponseEntity<DailyRevenueSummaryResponse> getDailyRevenueSummary(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        DailyRevenueSummaryResponse summary = revenueService.getDailyRevenueSummary(from, to);
        return ResponseEntity.ok(summary);
    }

    /**
     * Get daily revenue data only (for charts)
     * Example: /api/revenue/daily/data?from=2025-01-01&to=2025-01-31
     */
    @GetMapping("/daily/data")
    public ResponseEntity<List<DailyRevenueResponse>> getDailyRevenue(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<DailyRevenueResponse> dailyData = revenueService.getDailyRevenue(from, to);
        return ResponseEntity.ok(dailyData);
    }
}

