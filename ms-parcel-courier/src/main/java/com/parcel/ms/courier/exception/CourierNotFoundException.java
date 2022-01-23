package com.parcel.ms.courier.exception;

public class CourierNotFoundException extends RuntimeException {
    private String code;

    public CourierNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}