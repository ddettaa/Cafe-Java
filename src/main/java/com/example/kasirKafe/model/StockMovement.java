package com.example.kasirKafe.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "movement_type", length = 20, nullable = false)
    private String movementType; // IN, OUT, ADJUSTMENT

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false, length = 20)
    private String unit;

    @ManyToOne
    @JoinColumn(name = "ref_order_id")
    private OrderHeader refOrder;

    @Column(length = 255)
    private String note;

    @Column(name = "movement_time", nullable = false)
    private LocalDateTime movementTime;

    // getters & setters

    public Long getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public String getMovementType() {
        return movementType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public OrderHeader getRefOrder() {
        return refOrder;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getMovementTime() {
        return movementTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setRefOrder(OrderHeader refOrder) {
        this.refOrder = refOrder;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setMovementTime(LocalDateTime movementTime) {
        this.movementTime = movementTime;
    }
}
