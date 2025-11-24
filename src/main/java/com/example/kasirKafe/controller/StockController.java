package com.example.kasirKafe.controller;

import com.example.kasirKafe.dto.IngredientStockResponse;
import com.example.kasirKafe.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<IngredientStockResponse>> getAllStock() {
        return ResponseEntity.ok(service.getAllStock());
    }
}
