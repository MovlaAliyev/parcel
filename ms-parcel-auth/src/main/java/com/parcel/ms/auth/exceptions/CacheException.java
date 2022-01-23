package com.parcel.ms.auth.exceptions;

public class CacheException extends RuntimeException {
    private String code;

    public CacheException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}