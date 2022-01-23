package com.parcel.ms.reg.model;

import com.parcel.ms.reg.client.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
    private String name;
    private String surname;
    private String sessionKey;
    private String phoneNumber;
    private UserType userType;
}
