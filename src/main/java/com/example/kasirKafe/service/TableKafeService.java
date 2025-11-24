package com.example.kasirKafe.service;
import com.example.kasirKafe.dto.TableRequest;
import com.example.kasirKafe.dto.TableResponse;
import com.example.kasirKafe.model.TableKafe;
import com.example.kasirKafe.repository.TableKafeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableKafeService {
    private final TableKafeRepository tableKafeRepository;
    public TableKafeService(TableKafeRepository tableKafeRepository) {
        this.tableKafeRepository = tableKafeRepository;
    }
    private TableResponse toResponse(TableKafe table) {
        return new TableResponse(table.getId(), table.getName(), table.getStatus());
    }
    public List<TableResponse> getAll() {
        return tableKafeRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }
    public TableResponse getById(Long id) {
        TableKafe table = tableKafeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Table not found with id: " + id));
        return toResponse(table);
    }
    public TableResponse create (TableRequest request) {
        TableKafe t = new TableKafe();
        t.setName(request.getName());
        // Fix: Correct ternary logic to assign status properly
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            t.setStatus(request.getStatus());
        } else {
            t.setStatus("AVAILABLE");
        }
        TableKafe savedTable = tableKafeRepository.save(t);
        return toResponse(savedTable);
    }
    public TableResponse update(Long id, TableRequest request) {
        TableKafe t = tableKafeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Table not found with id: " + id));
        t.setName(request.getName());
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            t.setStatus(request.getStatus());
        } else {
            t.setStatus("AVAILABLE");
        }
        TableKafe updatedTable = tableKafeRepository.save(t);
        return toResponse(updatedTable);
    }
    public void delete(Long id) {
        TableKafe t = tableKafeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Table not found with id: " + id));
        tableKafeRepository.delete(t);
    }
}
