package com.example.kasirKafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.kasirKafe.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
