package com.parcel.ms.otp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpCreateRequest {
    private String email;
    private String subject;
    private String messageTemplate;
}
