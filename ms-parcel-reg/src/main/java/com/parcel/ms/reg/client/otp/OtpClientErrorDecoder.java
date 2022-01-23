package com.parcel.ms.reg.client.otp;

import com.parcel.ms.reg.exceptions.OtpException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class OtpClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 403 -> new OtpException("error.otp.invalidCode", "Invalid otp code");
            case 401 -> new OtpException("error.otp.noAttemptsLeft", "No attempts left");
            default -> new Exception("Internal Server error");
        };
    }
}
