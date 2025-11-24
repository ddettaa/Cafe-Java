package com.example.kasirKafe.dto;

import java.math.BigDecimal;

public class IngredientStockResponse {

    private Long id;
    private String name;
    private String unit;
    private BigDecimal balance;

    public IngredientStockResponse(Long id, String name, String unit, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
