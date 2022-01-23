package com.parcel.ms.courier.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.courier.model.OrderAssignedEvent;
import com.parcel.ms.courier.model.OrderCanceledEvent;
import com.parcel.ms.courier.model.OrderDeliveredEvent;
import com.parcel.ms.courier.model.OrderOnTheWayEvent;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Component
public class OrderAssignedQueue {

    private final ObjectMapper mapper;
    private final AmqpTemplate amqpTemplate;

    private final String orderCancelQueueName;
    private final String orderOnTheWayQueueName;
    private final String orderAssignedQueueName;
    private final String orderDeliveredQueueName;

    @Autowired
    public OrderAssignedQueue(
            ObjectMapper mapper,
            AmqpTemplate amqpTemplate,
            @Value("${queue.order.canceled-q}") String orderCancelQueueName,
            @Value("${queue.order.on-way-q}") String orderOnTheWayQueueName,
            @Value("${queue.order.assigned-q}") String orderAssignedQueueName,
            @Value("${queue.order.delivered-q}") String orderDeliveredQueueName) {
        this.mapper = mapper;
        this.amqpTemplate = amqpTemplate;
        this.orderCancelQueueName = orderCancelQueueName;
        this.orderOnTheWayQueueName = orderOnTheWayQueueName;
        this.orderAssignedQueueName = orderAssignedQueueName;
        this.orderDeliveredQueueName = orderDeliveredQueueName;
    }

    @SneakyThrows
    @Async
    @EventListener
    public void onAssigneeEvent(OrderAssignedEvent event) {
        log.debug("Assignee courier to order event to {}, event: {}", orderAssignedQueueName, event);
        amqpTemplate.convertAndSend(orderAssignedQueueName, mapper.writeValueAsString(event));
    }

    @SneakyThrows
    @Async
    @EventListener
    public void onCancelEvent(OrderCanceledEvent event) {
        log.debug("Cancel order event to {}, event: {}", orderCancelQueueName, event);
        amqpTemplate.convertAndSend(orderCancelQueueName, mapper.writeValueAsString(event));
    }

    @SneakyThrows
    @Async
    @EventListener
    public void onDeliveredEvent(OrderDeliveredEvent event) {
        log.debug("Deliver order event to {}, event: {}", orderDeliveredQueueName, event);
        amqpTemplate.convertAndSend(orderDeliveredQueueName, mapper.writeValueAsString(event));
    }

    @SneakyThrows
    @Async
    @EventListener
    public void onOnWayEvent(OrderOnTheWayEvent event) {
        log.debug("On way order event to {}, event: {}", orderOnTheWayQueueName, event);
        amqpTemplate.convertAndSend(orderOnTheWayQueueName, mapper.writeValueAsString(event));
    }
}

