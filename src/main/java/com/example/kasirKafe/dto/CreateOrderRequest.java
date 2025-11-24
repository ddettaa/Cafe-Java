package com.example.kasirKafe.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
public class CreateOrderRequest {

    @NotNull(message = "Table ID is required")
    private Long tableId;

    private List<AddOrderItemRequest> items;

    public Long getTableId() {
        return tableId;
    }
    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<AddOrderItemRequest> getItems() {
        return items;
    }
    public void setItems(List<AddOrderItemRequest> items) {
        this.items = items;
    }
}
