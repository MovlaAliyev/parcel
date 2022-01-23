package com.parcel.ms.auth.client.user;

import com.parcel.ms.auth.exceptions.UserException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class UserClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new UserException("error.UserNotFound", response.reason());
    }
}
