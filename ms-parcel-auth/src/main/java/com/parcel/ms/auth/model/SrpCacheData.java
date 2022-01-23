package com.parcel.ms.auth.model;

import com.nimbusds.srp6.SRP6ServerSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SrpCacheData implements Serializable {
    private User user;
    private SRP6ServerSession srp6ServerSession;
}
