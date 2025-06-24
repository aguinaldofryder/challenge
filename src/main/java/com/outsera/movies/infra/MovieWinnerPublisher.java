package com.outsera.movies.infra;

import com.outsera.core.domain.DomainEvent;
import com.outsera.core.domain.DomainEventHandler;
import com.outsera.core.domain.DomainEventPublisher;
import com.outsera.movies.domain.MovieWinnerEvent;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MovieWinnerPublisher implements DomainEventPublisher {

    private final DomainEventHandler<MovieWinnerEvent> eventHandler;

    @Override
    public void publishEvent(final DomainEvent event) {
        if (event instanceof MovieWinnerEvent) {
            eventHandler.handle((MovieWinnerEvent) event);
        }
    }
}
