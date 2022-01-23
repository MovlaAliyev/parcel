package com.parcel.ms.courier.exception;

public class CourierCreateException extends RuntimeException {
    private String code;

    public CourierCreateException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
