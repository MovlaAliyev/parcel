package com.parcel.ms.orders.client;

import com.parcel.ms.orders.exceptions.UserException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class UserClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 404 -> new UserException("error.UserNotFound", "User Not Found");
            default -> new Exception("Internal Server error");
        };
    }
}
