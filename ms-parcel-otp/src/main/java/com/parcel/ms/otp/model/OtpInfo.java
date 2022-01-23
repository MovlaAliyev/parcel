package com.parcel.ms.otp.model;

import com.parcel.ms.otp.enums.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OtpInfo implements Serializable {
    private String code;
    private OtpStatus status;
    private int attemptsLeft;
}
