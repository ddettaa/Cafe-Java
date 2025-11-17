package com.example.kasirKafe.controller;

import com.example.kasirKafe.model.MenuItem;
import com.example.kasirKafe.repository.MenuItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuitemController {

    private final MenuItemRepository menuItemRepository;
    public MenuitemController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    // GET /api/menu/items : Get all active menu items
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllActiveMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findByActiveTrue();
        return ResponseEntity.ok(menuItems);
    }

    
}
