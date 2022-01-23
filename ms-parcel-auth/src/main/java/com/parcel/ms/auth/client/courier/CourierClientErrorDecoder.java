package com.parcel.ms.auth.client.courier;

import com.parcel.ms.auth.exceptions.CourierException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CourierClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new CourierException("error.CourierNotFound", "Courier Not Found");
    }
}
