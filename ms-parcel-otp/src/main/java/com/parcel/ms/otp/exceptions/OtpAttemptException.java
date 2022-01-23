package com.parcel.ms.otp.exceptions;

public class OtpAttemptException extends RuntimeException {
    private String code;

    public OtpAttemptException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}