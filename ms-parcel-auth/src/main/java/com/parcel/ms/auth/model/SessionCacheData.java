package com.parcel.ms.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionCacheData implements Serializable {
    private String publicKey;
    private AccessTokenClaimsSet accessTokenClaimSet;
}
