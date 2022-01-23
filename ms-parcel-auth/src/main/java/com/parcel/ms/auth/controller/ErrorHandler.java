package com.parcel.ms.auth.controller;

import com.parcel.ms.auth.exceptions.AuthException;
import com.parcel.ms.auth.exceptions.CourierException;
import com.parcel.ms.auth.exceptions.UserException;
import com.parcel.ms.auth.model.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    private final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public ErrorResponse handle(AuthException ex) {
        log.error("Authentication exception: {}", ex.getMessage());
        return new ErrorResponse(
                ex.getCode(),
                ex.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserException.class)
    public ErrorResponse handle(UserException ex) {
        log.error("user exception: {}", ex.getMessage());
        return new ErrorResponse(
                ex.getCode(),
                ex.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourierException.class)
    public ErrorResponse handle(CourierException ex) {
        log.error("courier exception: {}", ex.getMessage());
        return new ErrorResponse(
                ex.getCode(),
                ex.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception ex) {
        log.error("Unhandled exception message: {}", ex.getMessage());
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.name()
        );
    }
}
