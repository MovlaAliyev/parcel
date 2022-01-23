package com.parcel.ms.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AccessTokenClaimsSet implements Serializable {
    long userId;
    long expirationTimeInSeconds;
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime expiresAt = createdAt.plusSeconds(expirationTimeInSeconds);
}
