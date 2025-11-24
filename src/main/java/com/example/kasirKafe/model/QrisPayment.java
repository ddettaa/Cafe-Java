package com.example.kasirKafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "qris_payment")
public class QrisPayment extends Payment {

    @Column(name = "qris_reference", length = 100)
    private String qrisReference;

    @Column(name = "issuer", length = 50)
    private String issuer;

    public String getQrisReference() {
        return qrisReference;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setQrisReference(String qrisReference) {
        this.qrisReference = qrisReference;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
