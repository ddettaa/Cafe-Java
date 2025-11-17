package com.example.kasirKafe.dto;
import java.math.BigDecimal;
public class MenuItemResponse {
    private Long id;
    private String name;
    private BigDecimal priceAmount;
    private String priceCurrency;
    private boolean active;

    private Long categoryId;
    private String categoryName;

    public MenuItemResponse() {}

    public MenuItemResponse(
        Long id,
        String name,
        BigDecimal priceAmount,
        String priceCurrency,
        boolean active,
        Long categoryId,
        String categoryName
    ){
        this.id = id;
        this.name = name;
        this.priceAmount = priceAmount;
        this.priceCurrency = priceCurrency;
        this.active = active;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public BigDecimal getPriceAmount() {
        return priceAmount;
    }
    public String getPriceCurrency() {
        return priceCurrency;
    }
    public boolean isActive() {
        return active;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
}
