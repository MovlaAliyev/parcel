package com.parcel.ms.user.exceptions;

public class UserException extends RuntimeException{
    private String code;

    public UserException(String code,String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
