package com.parcel.ms.reg.exceptions;

public class OtpException extends RuntimeException{

    private String code;

    public OtpException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}