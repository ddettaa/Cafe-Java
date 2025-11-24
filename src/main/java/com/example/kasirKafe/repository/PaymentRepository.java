package com.example.kasirKafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.kasirKafe.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
