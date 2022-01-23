package com.parcel.ms.reg.exceptions;

public class RegistrationStepException extends RuntimeException{

    private String code;

    public RegistrationStepException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
