package com.example.kasirKafe.repository;

import com.example.kasirKafe.model.MenuItemIngredient;
import com.example.kasirKafe.model.MenuItemIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemIngredientRepository
        extends JpaRepository<MenuItemIngredient, MenuItemIngredientId> {

    List<MenuItemIngredient> findByMenuItem_Id(Long menuItemId);
}
