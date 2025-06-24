package com.outsera.movies.domain;

import com.outsera.producers.domain.ProducerID;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateMovieCommand(
    Integer year,
    String title,
    String studios,
    Boolean winner,
    List<ProducerID> producers
) {
}
