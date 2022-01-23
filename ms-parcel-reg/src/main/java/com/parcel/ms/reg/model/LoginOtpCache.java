package com.parcel.ms.reg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginOtpCache implements Serializable {
    private String sessionKey;
    private String otpUUID;
}
