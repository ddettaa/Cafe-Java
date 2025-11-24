package com.example.kasirKafe.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_item_ingredient")
public class MenuItemIngredient {

    @EmbeddedId
    private MenuItemIngredientId id = new MenuItemIngredientId();

    @ManyToOne
    @MapsId("menuItemId")
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity; // jumlah per 1 porsi menu

    // getters & setters

    public MenuItemIngredientId getId() {
        return id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setId(MenuItemIngredientId id) {
        this.id = id;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
