package com.parcel.ms.orders.exceptions;

public class OrderChangeException extends RuntimeException {
    private String code;

    public OrderChangeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}