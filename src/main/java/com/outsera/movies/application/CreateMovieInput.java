package com.outsera.movies.application;

import lombok.Builder;

@Builder
public record CreateMovieInput(
    Integer year,
    String title,
    String studios,
    String producers,
    String winner
) {
}
