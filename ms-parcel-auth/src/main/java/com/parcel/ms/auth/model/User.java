package com.parcel.ms.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User implements Serializable {
    private long id;
    private String login;
    private String email;
    private String salt;
    private String verifier;
}
