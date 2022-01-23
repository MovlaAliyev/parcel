package com.parcel.ms.orders.queue.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.orders.model.OrderDoneEvent;
import com.parcel.ms.orders.service.OrderEventService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDoneQueue {

    private final ObjectMapper objectMapper;
    private final OrderEventService orderEventService;

    private final Logger log = LoggerFactory.getLogger(OrderDoneQueue.class);

    @Autowired
    public OrderDoneQueue(
            OrderEventService orderEventService,
            ObjectMapper objectMapper
    ) {
        this.orderEventService = orderEventService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @RabbitListener(queues = "${queue.order.delivered-q}")
    public void receiveMessage(String message) {
        log.debug("receive order delivered message {}", message);
        OrderDoneEvent info = objectMapper.readValue(message, OrderDoneEvent.class);
        orderEventService.doneOrder(info.getOrder().getId());
    }
}
