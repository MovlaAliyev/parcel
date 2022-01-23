package com.parcel.ms.orders.queue.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.orders.model.OrderCancelEvent;
import com.parcel.ms.orders.service.OrderEventService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCancelQueue {

    private final ObjectMapper objectMapper;
    private final OrderEventService orderEventService;

    private final Logger log = LoggerFactory.getLogger(OrderCancelQueue.class);

    @Autowired
    public OrderCancelQueue(
            OrderEventService orderEventService,
            ObjectMapper objectMapper
    ) {
        this.orderEventService = orderEventService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @RabbitListener(queues = "${queue.order.canceled-q}")
    public void receiveMessage(String message) {
        log.debug("receive order cancel message {}", message);
        OrderCancelEvent info = objectMapper.readValue(message, OrderCancelEvent.class);
        orderEventService.cancelOrder(info.getOrder().getId());
    }

}
