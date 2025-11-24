package com.example.kasirKafe.service;

import com.example.kasirKafe.dto.IngredientStockResponse;
import com.example.kasirKafe.model.Ingredient;
import com.example.kasirKafe.repository.IngredientRepository;
import com.example.kasirKafe.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final IngredientRepository ingredientRepo;
    private final StockMovementRepository stockMovementRepo;

    public StockService(IngredientRepository ingredientRepo,
                        StockMovementRepository stockMovementRepo) {
        this.ingredientRepo = ingredientRepo;
        this.stockMovementRepo = stockMovementRepo;
    }

    public List<IngredientStockResponse> getAllStock() {
        return ingredientRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private IngredientStockResponse toResponse(Ingredient ing) {
        var balance = stockMovementRepo.getStockBalance(ing.getId());
        return new IngredientStockResponse(
                ing.getId(),
                ing.getName(),
                ing.getUnit(),
                balance
        );
    }
}
