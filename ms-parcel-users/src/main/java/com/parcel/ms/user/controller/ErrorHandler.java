package com.parcel.ms.user.controller;

import com.parcel.ms.user.exceptions.UserCreateException;
import com.parcel.ms.user.exceptions.UserException;
import com.parcel.ms.user.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserException.class)
    public ErrorResponse handle(UserException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserCreateException.class)
    public ErrorResponse handle(UserCreateException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception ex) {
        return new ErrorResponse("error.unexpected", "Unknown error occurred");
    }

}
