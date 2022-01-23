package com.parcel.ms.courier.client.user.model;

import com.parcel.ms.courier.client.user.enums.UserStatus;
import com.parcel.ms.courier.client.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
