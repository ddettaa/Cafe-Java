package com.example.kasirKafe.repository;

import com.example.kasirKafe.model.OrderHeader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {

    @Query("""
           SELECT o FROM OrderHeader o
           WHERE (:status IS NULL OR o.orderStatus = :status)
             AND (:from IS NULL OR o.orderTime >= :from)
             AND (:to   IS NULL OR o.orderTime <= :to)
           ORDER BY o.orderTime DESC
           """)
    Page<OrderHeader> searchOrders(
            @Param("status") String status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable
    );
}
