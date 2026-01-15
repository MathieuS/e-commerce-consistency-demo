package com.github.mathieus.customerportal.service;

import com.github.mathieus.customerportal.entity.Outbox;
import com.github.mathieus.customerportal.messaging.RabbitMqPublisher;
import com.github.mathieus.customerportal.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutboxProcessingService {

    private static final Logger log = LoggerFactory.getLogger(OutboxProcessingService.class);
    private static final String PENDING_STATUS = "PENDING";
    private static final String SENT_STATUS = "SENT";

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private RabbitMqPublisher rabbitMqPublisher;

    @Scheduled(fixedRate = 500)
    @Transactional
    public void processOutboxEvents() {
        List<Outbox> pendingEvents = outboxRepository.findTop50ByStatusOrderByCreatedAtAsc(PENDING_STATUS);

        if (pendingEvents.isEmpty()) {
            log.debug("No event");
            return;
        }

        for (Outbox event : pendingEvents) {
            try {
                rabbitMqPublisher.publishEvent(event.getEventType(), event.getPayload());

                event.setStatus(SENT_STATUS);
                event.setSentAt(LocalDateTime.now());
                outboxRepository.save(event);

            } catch (Exception e) {
                //failed, nothing to do
            }
        }
    }
}