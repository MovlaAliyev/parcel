package com.parcel.ms.otp.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.otp.model.MailInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    private final String queueName;

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public QueueProducer(
            @Value("${queue.name}") String queueName,
            AmqpTemplate amqpTemplate,
            ObjectMapper objectMapper) {

        this.queueName    = queueName;
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToQueue(MailInfo info){

        try {
            amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(info));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
