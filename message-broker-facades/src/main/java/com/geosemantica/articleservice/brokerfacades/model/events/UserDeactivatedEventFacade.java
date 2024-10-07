package com.geosemantica.articleservice.brokerfacades.model.events;

import com.geosemantica.articleservice.facades.events.Event;

import java.util.UUID;

public interface UserDeactivatedEventFacade extends Event {
    UUID userId();
}
