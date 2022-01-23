package com.parcel.ms.courier.client.user;

import com.parcel.ms.courier.exception.UserException;
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
