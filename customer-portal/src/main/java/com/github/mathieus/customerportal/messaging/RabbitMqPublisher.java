package com.github.mathieus.customerportal.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqPublisher {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishEvent(String eventType, String payload) {
        rabbitTemplate.convertAndSend(eventType, payload);

        log.info("Outbox message sent, type : {}, Payload: {}", eventType, payload.substring(0, Math.min(payload.length(), 100)) + "...");

    }
}