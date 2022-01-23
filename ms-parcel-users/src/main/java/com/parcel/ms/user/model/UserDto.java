package com.parcel.ms.user.model;

import com.parcel.ms.user.enums.UserStatus;
import com.parcel.ms.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private long id;
    private String salt;
    private String name;
    private String email;
    private String login;
    private String surname;
    private String verifier;
    private UserType userType;
    private UserStatus status;
    private String phoneNumber;
}
