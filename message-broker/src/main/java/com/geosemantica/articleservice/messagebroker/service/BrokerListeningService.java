package com.geosemantica.articleservice.messagebroker.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.geosemantica.articleservice.facades.services.EventPublisher;
import com.geosemantica.articleservice.messagebroker.model.UserDeactivatedEvent;
import com.geosemantica.articleservice.brokerfacades.model.messages.UserEventMessage;

@RequiredArgsConstructor
@Service
public class BrokerListeningService {
    private final EventPublisher eventPublisher;

    @RabbitListener(queues = "${com.geosemantica.articleservice.message-broker.consuming.deactivated-users-queue}")
    private void listenUserDeactivated(@Payload @Valid UserEventMessage message) {
        eventPublisher.publishEvent(new UserDeactivatedEvent(message.getUserId()));
    }

}
