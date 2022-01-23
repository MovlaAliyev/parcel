package com.parcel.ms.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenClaimsSet implements Serializable {
    long userId;
    int count;
    long expirationTimeInSeconds;
    LocalDateTime expiresAt;
}
