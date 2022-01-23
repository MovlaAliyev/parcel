package com.parcel.ms.otp.controller;

import com.parcel.ms.otp.exceptions.OtpAttemptException;
import com.parcel.ms.otp.exceptions.OtpCacheException;
import com.parcel.ms.otp.exceptions.OtpException;
import com.parcel.ms.otp.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OtpCacheException.class)
    public ErrorResponse handle(OtpCacheException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OtpException.class)
    public ErrorResponse handle(OtpException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(OtpAttemptException.class)
    public ErrorResponse handle(OtpAttemptException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception ex) {
        return new ErrorResponse("error.unexpected", "Unknown error occurred");
    }

}
