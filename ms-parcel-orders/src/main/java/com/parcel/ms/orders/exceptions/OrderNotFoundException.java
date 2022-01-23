package com.parcel.ms.orders.exceptions;

public class OrderNotFoundException extends RuntimeException {
    private String code;

    public OrderNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
