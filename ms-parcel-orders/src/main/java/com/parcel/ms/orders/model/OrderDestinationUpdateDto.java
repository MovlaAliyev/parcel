package com.parcel.ms.orders.model;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ApiOperation("Model for update oder destination")
public class OrderDestinationUpdateDto {
    private double lat;
    private double lon;
}
