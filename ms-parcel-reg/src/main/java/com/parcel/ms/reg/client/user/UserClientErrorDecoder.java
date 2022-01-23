package com.parcel.ms.reg.client.user;

import com.parcel.ms.reg.exceptions.UserException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class UserClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new UserException("error.UserNotFound", "User Not Found");
            default -> new Exception("Internal Server error");
        };
    }
}