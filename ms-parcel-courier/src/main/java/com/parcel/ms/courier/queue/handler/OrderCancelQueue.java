package com.parcel.ms.courier.queue.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.courier.model.OrderCanceledEvent;
import com.parcel.ms.courier.service.CourierService;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OrderCancelQueue {

    private final ObjectMapper objectMapper;
    private final CourierService courierService;

    @Autowired
    public OrderCancelQueue(
            ObjectMapper objectMapper,
            CourierService courierService) {
        this.objectMapper = objectMapper;
        this.courierService = courierService;
    }

    @SneakyThrows
    @RabbitListener(queues = "${queue.courier.canceled-q}")
    public void receiveMessage(String message) {
        OrderCanceledEvent info = objectMapper.readValue(message, OrderCanceledEvent.class);
        courierService.cancelOrderByUser(info.getOrder().getCourierId());
    }

}
