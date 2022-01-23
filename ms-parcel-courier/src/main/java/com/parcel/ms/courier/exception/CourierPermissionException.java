package com.parcel.ms.courier.exception;

public class CourierPermissionException extends RuntimeException {
    private String code;

    public CourierPermissionException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}