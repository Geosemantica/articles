package com.geosemantica.articleservice.service.services;

import com.geosemantica.articleservice.facades.events.Event;
import com.geosemantica.articleservice.facades.services.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisher implements ApplicationEventPublisherAware, EventPublisher {
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
