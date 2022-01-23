package com.parcel.ms.mail.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcel.ms.mail.model.MailInfo;
import com.parcel.ms.mail.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class QueueListener {

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @Autowired
    public QueueListener(
            MailService mailService,
            ObjectMapper objectMapper
    ) {
        this.mailService = mailService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${queue.mail-q}")
    public void receiveMessage(String message) {
        try {
            MailInfo info = objectMapper.readValue(message, MailInfo.class);
            mailService.sendEmail(info);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
