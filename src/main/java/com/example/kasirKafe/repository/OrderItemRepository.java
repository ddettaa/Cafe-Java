package com.example.kasirKafe.repository;

import com.example.kasirKafe.model.OrderItem;
import com.example.kasirKafe.model.OrderItemId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

    int countByOrder_Id(Long orderId);
    List<OrderItem> findByOrder_Id(Long orderId);
}
