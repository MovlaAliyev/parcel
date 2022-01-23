package com.parcel.ms.reg.exceptions;

public class OtpCacheException extends RuntimeException{

    private String code;

    public OtpCacheException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
