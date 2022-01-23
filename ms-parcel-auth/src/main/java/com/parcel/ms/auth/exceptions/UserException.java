package com.parcel.ms.auth.exceptions;

public class UserException extends RuntimeException {
    private String code;

    public UserException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}