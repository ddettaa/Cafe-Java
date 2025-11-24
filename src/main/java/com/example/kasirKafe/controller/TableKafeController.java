package com.example.kasirKafe.controller;

import com.example.kasirKafe.dto.TableRequest;
import com.example.kasirKafe.dto.TableResponse;
import com.example.kasirKafe.service.TableKafeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = "*")
public class TableKafeController {

    private final TableKafeService service;

    public TableKafeController(TableKafeService service) {
        this.service = service;
    }

    // GET /api/tables
    @GetMapping
    public ResponseEntity<List<TableResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/tables/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TableResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // POST /api/tables
    @PostMapping
    public ResponseEntity<TableResponse> create(@Valid @RequestBody TableRequest request) {
        TableResponse created = service.create(request);
        return ResponseEntity
                .created(URI.create("/api/tables/" + created.getId()))
                .body(created);
    }

    // PUT /api/tables/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TableResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TableRequest request
    ) {
        TableResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/tables/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
