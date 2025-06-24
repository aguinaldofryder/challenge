package com.outsera.movies.infra;

import com.outsera.core.domain.DomainEventHandler;
import com.outsera.movies.domain.MovieWinnerEvent;
import com.outsera.producers.application.wins.SetWinToProducerInput;
import com.outsera.producers.application.wins.SetWinToProducerUseCase;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MovieWinnerHandler implements DomainEventHandler<MovieWinnerEvent> {

    private final SetWinToProducerUseCase setWinToProducerUseCase;

    @Override
    public void handle(final MovieWinnerEvent event) {
        final SetWinToProducerInput input = SetWinToProducerInput.builder()
            .producerId(event.getProducerId())
            .yearOfWin(event.getYearOfWin())
            .build();
        setWinToProducerUseCase.execute(input);
    }
}
