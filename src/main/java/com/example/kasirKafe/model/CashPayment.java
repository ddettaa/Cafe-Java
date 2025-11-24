package com.example.kasirKafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cash_payment")
public class CashPayment extends Payment {
    
    @Column(name = "cash_received", precision = 15, scale = 2, nullable = false)
    private BigDecimal cashReceived;

    @Column(name = "change_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal changeAmount;

    public BigDecimal getCashReceived() {
        return cashReceived;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setCashReceived(BigDecimal cashReceived) {
        this.cashReceived = cashReceived;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }
}
