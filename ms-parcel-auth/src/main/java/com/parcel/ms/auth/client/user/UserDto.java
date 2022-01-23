package com.parcel.ms.auth.client.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private long id;
    private String salt;
    private String email;
    private String login;
    private String verifier;
}
