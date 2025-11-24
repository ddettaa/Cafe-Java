package com.example.kasirKafe.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class OrderItemId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "line_number")
    private Integer lineNumber;
    
    public OrderItemId(){

    }
    public  OrderItemId(Long orderId, Integer lineNumber){
        this.orderId = orderId;
        this.lineNumber = lineNumber;
    }
    public Long getOrderId() {
        return orderId;
    }
    public Integer getLineNumber() {
        return lineNumber;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
    @Override
    public boolean equals(Object o){
        if (this == o ) return true;
        if (! (o instanceof OrderItemId that)) return false;
        return Objects.equals(orderId, that.orderId) && Objects.equals(lineNumber, that.lineNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(orderId, lineNumber);
    }
}

