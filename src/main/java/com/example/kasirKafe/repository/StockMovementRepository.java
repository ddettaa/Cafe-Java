package com.example.kasirKafe.repository;

import com.example.kasirKafe.model.StockMovement;
import com.example.kasirKafe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByIngredient(Ingredient ingredient);

    @Query("""
           SELECT COALESCE(SUM(
                    CASE WHEN sm.movementType = 'IN' THEN sm.quantity
                         WHEN sm.movementType = 'OUT' THEN -sm.quantity
                         ELSE 0 END
                  ), 0)
           FROM StockMovement sm
           WHERE sm.ingredient.id = :ingredientId
           """)
    BigDecimal getStockBalance(Long ingredientId);
}
