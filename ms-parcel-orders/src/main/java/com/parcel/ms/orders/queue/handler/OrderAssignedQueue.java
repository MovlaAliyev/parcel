package com.parcel.ms.orders.queue.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.orders.model.OrderAssignedEvent;
import com.parcel.ms.orders.service.OrderEventService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderAssignedQueue {

    private final ObjectMapper objectMapper;
    private final OrderEventService orderEventService;

    private final Logger log = LoggerFactory.getLogger(OrderAssignedQueue.class);

    @Autowired
    public OrderAssignedQueue(
            OrderEventService orderEventService,
            ObjectMapper objectMapper
    ) {
        this.orderEventService = orderEventService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @RabbitListener(queues = "${queue.order.assigned-q}")
    public void receiveMessage(String message) {
        log.debug("receive order assigned message {}", message);
        OrderAssignedEvent info = objectMapper.readValue(message, OrderAssignedEvent.class);
        orderEventService.assignOrder(info.getOrder().getId(), info.getOrder().getCourierId());
    }

}
