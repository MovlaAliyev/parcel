package com.parcel.ms.reg.exceptions;

public class RegistrationCacheException extends RuntimeException{

    private String code;

    public RegistrationCacheException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}