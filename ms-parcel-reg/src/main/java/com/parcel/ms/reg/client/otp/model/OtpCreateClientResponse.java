package com.parcel.ms.reg.client.otp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OtpCreateClientResponse {
    private String otp;
    private String uuid;
    private int resendDelay;
    private int expirationTime;
}
