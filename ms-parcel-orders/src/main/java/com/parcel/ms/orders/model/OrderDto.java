package com.parcel.ms.orders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDto {

    private double pickupLat;
    private double pickupLon;

    private double destinationLat;
    private double destinationLon;

    private String instruction;

    private double packageWeight;

    private String pickupPhoneNumber;
    private String deliveryPhoneNumber;

}
