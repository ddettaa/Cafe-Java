package com.example.kasirKafe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id = new OrderItemId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderHeader order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal unitPriceAmount;

    @Column(name = "unit_price_currency", length = 3)
    private String unitPriceCurrency = "IDR";

    @Column(name = "line_total_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal lineTotalAmount;

    @Column(name = "line_total_currency", length = 3)
    private String lineTotalCurrency = "IDR";

    // getters & setters

    public OrderItemId getId() {
        return id;
    }

    public OrderHeader getOrder() {
        return order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPriceAmount() {
        return unitPriceAmount;
    }

    public String getUnitPriceCurrency() {
        return unitPriceCurrency;
    }

    public BigDecimal getLineTotalAmount() {
        return lineTotalAmount;
    }

    public String getLineTotalCurrency() {
        return lineTotalCurrency;
    }

    public void setId(OrderItemId id) {
        this.id = id;
    }

    public void setOrder(OrderHeader order) {
        this.order = order;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPriceAmount(BigDecimal unitPriceAmount) {
        this.unitPriceAmount = unitPriceAmount;
    }

    public void setUnitPriceCurrency(String unitPriceCurrency) {
        this.unitPriceCurrency = unitPriceCurrency;
    }

    public void setLineTotalAmount(BigDecimal lineTotalAmount) {
        this.lineTotalAmount = lineTotalAmount;
    }

    public void setLineTotalCurrency(String lineTotalCurrency) {
        this.lineTotalCurrency = lineTotalCurrency;
    }
}
