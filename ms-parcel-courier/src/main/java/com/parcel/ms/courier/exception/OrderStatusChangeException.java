package com.parcel.ms.courier.exception;

public class OrderStatusChangeException extends RuntimeException {
    private String code;

    public OrderStatusChangeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}