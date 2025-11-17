package com.example.kasirKafe.controller;

import com.example.kasirKafe.service.MenuItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.kasirKafe.dto.MenuItemRequest;
import com.example.kasirKafe.dto.MenuItemResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.net.URI;
@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuitemController {

    private final MenuItemService service;
    public MenuitemController(MenuItemService service) {
        this.service = service;
    }

   @GetMapping
   public ResponseEntity<List<MenuItemResponse>> getAllActive(){
    return ResponseEntity.ok(service.getAllActive());
   }
   @GetMapping("/{id}")
   public ResponseEntity<MenuItemResponse> getById(@PathVariable Long id){
    return ResponseEntity.ok(service.getById(id));
   }

   @PostMapping
   public ResponseEntity<MenuItemResponse> create(@Valid @RequestBody MenuItemRequest request){
    MenuItemResponse created = service.create(request);
    return ResponseEntity
    .created(URI.create("/api/menu/" + created.getId()))
    .body(created);
   }

   @PutMapping("/{id}")
   public ResponseEntity<MenuItemResponse> update(
    @PathVariable Long id,
    @Valid @RequestBody MenuItemRequest request
   ){
    MenuItemResponse updated = service.update(id, request);
    return ResponseEntity.ok(updated);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id){
    service.delete(id);
    return ResponseEntity.noContent().build();
   }

    
}
