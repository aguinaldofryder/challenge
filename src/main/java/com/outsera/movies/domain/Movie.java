package com.outsera.movies.domain;

import com.outsera.core.domain.Entity;
import com.outsera.producers.domain.ProducerID;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Movie extends Entity {
    private MovieID id;
    private Integer year;
    private String title;
    private String studios;
    private Boolean winner;
    private List<ProducerID> producers;

    @Builder
    private Movie(final MovieID id,
        final Integer year,
        final String title,
        final String studios,
        final Boolean winner,
        final List<ProducerID> producers) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.winner = winner;
        this.producers = producers;

        //validate(); No construtor ficaram as validações das regras de negócio
    }

    public static Movie create(CreateMovieCommand command) {
        final Movie movie = Movie.builder()
            .id(MovieID.unique())
            .year(command.year())
            .title(command.title())
            .studios(command.studios())
            .winner(command.winner())
            .producers(command.producers())
            .build();

        if (movie.winner) {
            movie.getProducers()
                .forEach(producerID -> movie.registerEvent(MovieWinnerEvent.builder()
                    .producerId(producerID)
                    .yearOfWin(movie.getYear())
                    .build()));
        }

        return movie;
    }
}
