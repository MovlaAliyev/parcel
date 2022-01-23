package com.parcel.ms.reg.client.user.model;

import com.parcel.ms.reg.client.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String salt;
    private String name;
    private String email;
    private String login;
    private String surname;
    private String verifier;
    private UserType userType;
    private String phoneNumber;
}
