package com.example.kasirKafe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    @JsonBackReference
    private OrderHeader order;

    @Column(name = "paid_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal paidAmount;

    @Column(name = "paid_currency", length = 3)
    private String paidCurrency = "IDR";

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Column(name = "payment_type", length = 20, nullable = false)
    private String paymentType; // CASH / QRIS / dll

    // getters & setters

    public Long getId() {
        return id;
    }

    public OrderHeader getOrder() {
        return order;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public String getPaidCurrency() {
        return paidCurrency;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(OrderHeader order) {
        this.order = order;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setPaidCurrency(String paidCurrency) {
        this.paidCurrency = paidCurrency;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
