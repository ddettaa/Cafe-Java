package com.example.kasirKafe.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CheckoutRequest {

    @NotNull
    private String paymentType; // "CASH" atau "QRIS"

    @NotNull
    private BigDecimal paidAmount; // total yang dibayar pelanggan (sama dengan total order)

    // hanya untuk CASH
    private BigDecimal cashReceived;

    // hanya untuk QRIS
    private String qrisReference;
    private String issuer;

    public String getPaymentType() {
        return paymentType;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getCashReceived() {
        return cashReceived;
    }

    public String getQrisReference() {
        return qrisReference;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setCashReceived(BigDecimal cashReceived) {
        this.cashReceived = cashReceived;
    }

    public void setQrisReference(String qrisReference) {
        this.qrisReference = qrisReference;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
