package com.example.kasirKafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.kasirKafe.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = """
           SELECT DATE(p.payment_time) as date,
                  COALESCE(SUM(p.paid_amount), 0) as totalRevenue,
                  COUNT(p.id) as orderCount,
                  COALESCE(SUM(CASE WHEN p.payment_type = 'CASH' THEN p.paid_amount ELSE 0 END), 0) as cashRevenue,
                  COALESCE(SUM(CASE WHEN p.payment_type = 'QRIS' THEN p.paid_amount ELSE 0 END), 0) as qrisRevenue,
                  COUNT(CASE WHEN p.payment_type = 'CASH' THEN 1 END) as cashOrderCount,
                  COUNT(CASE WHEN p.payment_type = 'QRIS' THEN 1 END) as qrisOrderCount
           FROM payment p
           WHERE p.payment_time >= :fromDate
             AND p.payment_time <= :toDate
           GROUP BY DATE(p.payment_time)
           ORDER BY date ASC
           """, nativeQuery = true)
    List<Object[]> getDailyRevenue(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
           SELECT COALESCE(SUM(p.paidAmount), 0)
           FROM Payment p
           WHERE p.paymentTime >= :fromDate
             AND p.paymentTime <= :toDate
           """)
    BigDecimal getTotalRevenue(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
           SELECT COALESCE(SUM(p.paidAmount), 0)
           FROM Payment p
           WHERE p.paymentTime >= :fromDate
             AND p.paymentTime <= :toDate
             AND p.paymentType = :paymentType
           """)
    BigDecimal getRevenueByPaymentType(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("paymentType") String paymentType
    );
}
