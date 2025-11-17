package com.example.kasirKafe.service;

import com.example.kasirKafe.dto.CategoryRequest;
import com.example.kasirKafe.dto.CategoryResponse;
import com.example.kasirKafe.model.MenuCategory;
import com.example.kasirKafe.repository.MenuCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryService {
    private final MenuCategoryRepository categoryRepository;

    public CategoryService(MenuCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    //helper mapper

    private CategoryResponse toResponse(MenuCategory entity) {
        return new CategoryResponse(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
        );
            }
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CategoryResponse getById(Long Id){
        MenuCategory cat = categoryRepository.findById(Id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + Id));
        return toResponse(cat);
    }

    public CategoryResponse create(CategoryRequest request) {
        MenuCategory cat = new MenuCategory();
        cat.setName(request.getName());
        cat.setDescription(request.getDescription());
        MenuCategory savedCat = categoryRepository.save(cat);
        return toResponse(savedCat);
    }
    
    public CategoryResponse update(Long Id, CategoryRequest request) {
        MenuCategory cat = categoryRepository.findById(Id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + Id));
        cat.setName(request.getName());
        cat.setDescription(request.getDescription());
        MenuCategory savedCat = categoryRepository.save(cat);
        return toResponse(savedCat);
    }

    public void delete(Long Id) {
        MenuCategory cat = categoryRepository.findById(Id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + Id));
        categoryRepository.delete(cat);
    }
}
