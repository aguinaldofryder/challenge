package com.outsera.movies.application;

import com.outsera.movies.domain.CreateMovieCommand;
import com.outsera.movies.domain.Movie;
import com.outsera.movies.domain.MovieRepository;
import com.outsera.producers.application.create.GetOrCreateProducerUseCase;
import com.outsera.producers.domain.ProducerID;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateMovieUseCase {

    private final MovieRepository repository;

    private final GetOrCreateProducerUseCase getOrCreateProducerUseCase;

    public void execute(CreateMovieInput input) {

        final List<ProducerID> producerIDs = Arrays.stream(input.producers().split(",| and "))
            .map(String::trim)
            .map(getOrCreateProducerUseCase::execute)
            .toList();

        final var command = CreateMovieCommand.builder()
            .year(input.year())
            .title(input.title())
            .studios(input.studios())
            .winner(Objects.equals("yes", input.winner()))
            .producers(producerIDs)
            .build();
        final var movie = Movie.create(command);
        repository.create(movie);
    }
}
