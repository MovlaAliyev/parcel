package com.parcel.ms.otp.exceptions;

public class OtpException extends RuntimeException {

    private String code;

    public OtpException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
