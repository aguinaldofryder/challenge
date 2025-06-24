package com.outsera.producers.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProducerID {
    private final UUID value;

    private ProducerID(final UUID value) {
        this.value = value;
    }

    public static ProducerID from(final UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("ProducerID cannot be null");
        }
        return new ProducerID(value);
    }

    public static ProducerID unique() {
        return new ProducerID(UUID.randomUUID());
    }
}
