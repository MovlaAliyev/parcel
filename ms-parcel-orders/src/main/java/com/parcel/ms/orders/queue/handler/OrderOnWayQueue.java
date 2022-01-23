package com.parcel.ms.orders.queue.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.orders.model.OrderOnWayEvent;
import com.parcel.ms.orders.service.OrderEventService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderOnWayQueue {

    private final ObjectMapper objectMapper;
    private final OrderEventService orderEventService;

    private final Logger log = LoggerFactory.getLogger(OrderOnWayQueue.class);

    @Autowired
    public OrderOnWayQueue(
            OrderEventService orderEventService,
            ObjectMapper objectMapper
    ) {
        this.orderEventService = orderEventService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @RabbitListener(queues = "${queue.order.on-way-q}")
    public void receiveMessage(String message) {
        log.debug("receive order onway message {}", message);
        OrderOnWayEvent info = objectMapper.readValue(message, OrderOnWayEvent.class);
        orderEventService.onTheWayOrder(info.getOrder().getId());
    }

}
