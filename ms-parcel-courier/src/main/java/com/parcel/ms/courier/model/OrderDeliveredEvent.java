package com.parcel.ms.courier.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDeliveredEvent {
    private Order order;
    private String transactionId;
}
