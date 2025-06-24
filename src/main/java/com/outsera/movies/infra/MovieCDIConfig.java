package com.outsera.movies.infra;

import com.outsera.movies.application.CreateMovieUseCase;
import com.outsera.movies.domain.MovieRepository;
import com.outsera.producers.application.create.GetOrCreateProducerUseCase;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import lombok.RequiredArgsConstructor;

@Dependent
@RequiredArgsConstructor
public class MovieCDIConfig {

    private final MovieRepository repository;

    @Produces
    @RequestScoped
    public CreateMovieUseCase createMovieUseCase(final GetOrCreateProducerUseCase getOrCreateProducerUseCase) {
        return new CreateMovieUseCase(repository, getOrCreateProducerUseCase);
    }
}
