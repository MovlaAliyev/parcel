package com.parcel.ms.reg.exceptions;

public class RegistrationException extends RuntimeException{

    private String code;

    public RegistrationException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
