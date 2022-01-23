package com.parcel.ms.reg.client.otp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpCreateClientRequest {
    private String email;
    private String subject;
    private String messageTemplate;
}
