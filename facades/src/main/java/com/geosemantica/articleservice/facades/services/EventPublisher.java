package com.geosemantica.articleservice.facades.services;


import com.geosemantica.articleservice.facades.events.Event;

public interface EventPublisher {
    /**
     * Publish the specified event.
     *
     * @param event Event.
     */
    void publishEvent(Event event);
}
