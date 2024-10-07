package com.geosemantica.articleservice.messagebroker.model;

import com.geosemantica.articleservice.brokerfacades.model.events.UserDeactivatedEventFacade;

import java.util.UUID;

public record UserDeactivatedEvent(UUID userId) implements UserDeactivatedEventFacade {
}
