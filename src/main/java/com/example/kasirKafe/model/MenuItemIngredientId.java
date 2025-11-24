package com.example.kasirKafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuItemIngredientId implements Serializable {

    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(name = "ingredient_id")
    private Long ingredientId;

    public MenuItemIngredientId() {
    }

    public MenuItemIngredientId(Long menuItemId, Long ingredientId) {
        this.menuItemId = menuItemId;
        this.ingredientId = ingredientId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItemIngredientId that)) return false;
        return Objects.equals(menuItemId, that.menuItemId)
                && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemId, ingredientId);
    }
}
