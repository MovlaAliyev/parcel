package com.parcel.ms.reg.exceptions;

public class UserException extends RuntimeException{

    private String code;

    public UserException(String code, String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
