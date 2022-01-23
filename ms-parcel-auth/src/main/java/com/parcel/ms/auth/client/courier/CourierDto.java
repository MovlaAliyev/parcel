package com.parcel.ms.auth.client.courier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CourierDto{
    private long id;
    private String salt;
    private String login;
    private String verifier;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
}
