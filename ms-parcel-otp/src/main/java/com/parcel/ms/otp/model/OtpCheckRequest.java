package com.parcel.ms.otp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OtpCheckRequest {
    private String uuid;
    private String otpCode;
}
