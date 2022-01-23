package com.parcel.ms.otp.exceptions;

public class OtpCacheException  extends RuntimeException {
    private String code;

    public OtpCacheException(String code, String message) {
        super(message);

        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
