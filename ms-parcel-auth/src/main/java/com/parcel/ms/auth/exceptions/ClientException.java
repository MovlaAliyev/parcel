package com.parcel.ms.auth.exceptions;

public class ClientException extends RuntimeException {
    private String code;

    public ClientException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
