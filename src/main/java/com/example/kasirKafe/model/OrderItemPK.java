package com.example.kasirKafe.model;

import java.io.Serializable;
import java.util.Objects;

public class OrderItemPK implements Serializable {

    private Long orderId;
    private Integer lineNumber;

    // default constructor
    public OrderItemPK() {}

    // GETTER & SETTER
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(lineNumber, that.lineNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(orderId, lineNumber);
    }
}
