package com.example.kasirKafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.kasirKafe.model.MenuItem;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByActiveTrue();
}
