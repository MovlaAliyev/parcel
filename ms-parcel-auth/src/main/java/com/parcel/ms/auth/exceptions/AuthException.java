package com.parcel.ms.auth.exceptions;

public class AuthException extends RuntimeException {
    private String code;

    public AuthException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
