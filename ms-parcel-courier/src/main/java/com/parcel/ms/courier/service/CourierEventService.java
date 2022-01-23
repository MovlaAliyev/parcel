package com.parcel.ms.courier.service;

import com.parcel.ms.courier.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class CourierEventService {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public CourierEventService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishOrderAssigned(long courierId, long orderId) {
        OrderAssignedEvent event = new OrderAssignedEvent(new Order(orderId, courierId), UUID.randomUUID().toString());
        publisher.publishEvent(event);
    }

    public void publishOrderDelivered(long courierId, long orderId) {
        OrderDeliveredEvent event = new OrderDeliveredEvent(new Order(orderId, courierId), UUID.randomUUID().toString());
        publisher.publishEvent(event);
    }

    public void publishOrderCanceled(long courierId, long orderId) {
        OrderCanceledEvent event = new OrderCanceledEvent(new Order(orderId, courierId), UUID.randomUUID().toString());
        publisher.publishEvent(event);
    }

    public void publishOrderOnTheWay(long courierId, long orderId) {
        OrderOnTheWayEvent event = new OrderOnTheWayEvent(new Order(orderId, courierId), UUID.randomUUID().toString());
        publisher.publishEvent(event);
    }
}
