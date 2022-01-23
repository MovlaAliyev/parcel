package com.parcel.ms.auth.exceptions;

public class CourierException extends RuntimeException {
    private String code;

    public CourierException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}