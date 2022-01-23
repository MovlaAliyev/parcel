package com.parcel.ms.courier.model;

import com.parcel.ms.courier.enums.CourierStatus;
import com.parcel.ms.courier.enums.CourierTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CourierDto {
    private long id;
    private String salt;
    private String login;
    private String verifier;

    private String email;
    private String name;
    private String surname;
    private String phoneNumber;

    private CourierTypes courierTypes;
    private CourierStatus courierStatus;
}
