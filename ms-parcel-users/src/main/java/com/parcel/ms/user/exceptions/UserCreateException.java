package com.parcel.ms.user.exceptions;

public class UserCreateException extends RuntimeException{
    private String code;

    public UserCreateException(String code,String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
