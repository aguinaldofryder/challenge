package com.outsera.core.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public void registerEvent(final DomainEvent event) {
        if (event == null) {
            return;
        }

        this.domainEvents.add(event);
    }


    public void publishDomainEvents(final DomainEventPublisher publisher) {
        if (publisher == null) {
            return;
        }

        this.domainEvents.forEach(publisher::publishEvent);

        this.domainEvents.clear();
    }
}
