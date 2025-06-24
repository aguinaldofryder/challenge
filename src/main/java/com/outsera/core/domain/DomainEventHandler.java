package com.outsera.core.domain;

public interface DomainEventHandler<T extends DomainEvent> {
    void handle(T event);
}
