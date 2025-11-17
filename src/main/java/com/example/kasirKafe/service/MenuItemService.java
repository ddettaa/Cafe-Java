package com.example.kasirKafe.service;

import com.example.kasirKafe.dto.MenuItemRequest;
import com.example.kasirKafe.dto.MenuItemResponse;
import com.example.kasirKafe.model.MenuItem;
import com.example.kasirKafe.model.MenuCategory;
import com.example.kasirKafe.repository.MenuItemRepository;
import com.example.kasirKafe.repository.MenuCategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service

public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryRepository categoryRepository;
    public MenuItemService(MenuItemRepository menuItemRepository, MenuCategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }
    private MenuItemResponse toResponse(MenuItem item) {
        Long categoryId = null;
        String categoryName = null;

        if (item.getCategory() != null) {
            categoryId = item.getCategory().getId();
            categoryName = item.getCategory().getName();
        }
        return new MenuItemResponse(
            item.getId(),
            item.getName(),
            item.getPriceAmount(),
            item.getPriceCurrency(),
            item.isActive(),
            categoryId,
            categoryName
        );
    }

    public List<MenuItemResponse> getAllActive() {
        return menuItemRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public MenuItemResponse getById(Long id){
        MenuItem item = menuItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
        return toResponse(item);
    }
    public MenuItemResponse create (MenuItemRequest request){
        MenuCategory category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setPriceAmount(request.getPriceAmount());
        item.setPriceCurrency("IDR");
        item.setCategory(category);
        Boolean active = request.isActive();
        item.setActive(active == null ? false : active);
        MenuItem savedItem = menuItemRepository.save(item);
        return toResponse(savedItem);
    }
    public MenuItemResponse update(Long id, MenuItemRequest request){
        MenuItem item = menuItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
        MenuCategory category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
        item.setName(request.getName());
        item.setPriceAmount(request.getPriceAmount());
        item.setPriceCurrency("IDR");
        item.setCategory(category);
        Boolean active = request.isActive();
        item.setActive(active == null ? false : active);
        MenuItem updatedItem = menuItemRepository.save(item);
        return toResponse(updatedItem);
    }
    public void delete(Long id){
        MenuItem item = menuItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
        menuItemRepository.delete(item);
    }
}
