package com.parcel.ms.orders.controller;

import com.parcel.ms.orders.exceptions.OrderChangeException;
import com.parcel.ms.orders.exceptions.OrderNotFoundException;
import com.parcel.ms.orders.exceptions.OrderPermissionException;
import com.parcel.ms.orders.exceptions.UserException;
import com.parcel.ms.orders.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderChangeException.class)
    public ErrorResponse handle(OrderChangeException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ErrorResponse handle(OrderNotFoundException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderPermissionException.class)
    public ErrorResponse handle(OrderPermissionException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserException.class)
    public ErrorResponse handle(UserException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception ex) {
        logger.error(ex.getMessage());
        return new ErrorResponse("error.unexpected", "Unknown error occurred");
    }

}
