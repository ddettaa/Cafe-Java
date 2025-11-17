package com.example.kasirKafe.controller;

import org.springframework.web.bind.annotation.*;
import com.example.kasirKafe.service.CategoryService;
import org.springframework.http.ResponseEntity;
import com.example.kasirKafe.dto.CategoryRequest;
import com.example.kasirKafe.dto.CategoryResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }
    // GET /api/category/{id} : Get a category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    // GET /api/category : Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    // PUT /api/category/{id} : Update a category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody CategoryRequest request
    ){
        CategoryResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/category/{id} : Delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/category : Create a new category
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request){
        CategoryResponse created = service.create(request);
        //buat location header kita bisa menggunakan URI.create("/api/category/" + created.getId())
        URI location = URI.create("/api/category/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
}
