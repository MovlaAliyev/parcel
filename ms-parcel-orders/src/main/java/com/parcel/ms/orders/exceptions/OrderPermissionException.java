package com.parcel.ms.orders.exceptions;

public class OrderPermissionException extends RuntimeException {
    private String code;

    public OrderPermissionException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}