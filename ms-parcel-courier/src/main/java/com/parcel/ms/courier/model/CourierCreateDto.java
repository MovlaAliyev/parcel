package com.parcel.ms.courier.model;

import com.parcel.ms.courier.enums.CourierTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CourierCreateDto {
    private String salt;
    private String login;
    private String verifier;

    private String email;
    private String name;
    private String surname;
    private String phoneNumber;

    private CourierTypes courierType;
}
