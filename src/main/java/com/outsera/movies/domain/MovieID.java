package com.outsera.movies.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MovieID {
    private final UUID value;

    private MovieID(final UUID value) {
        this.value = value;
    }

    public static MovieID from(final UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("MovieID cannot be null");
        }
        return new MovieID(value);
    }

    public static MovieID unique() {
        return new MovieID(UUID.randomUUID());
    }
}
