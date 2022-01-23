package com.parcel.ms.orders.model;

import com.parcel.ms.orders.dao.OrderDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDoneEvent {
    private OrderDao order;
    private String transactionId;
}
