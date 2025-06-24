package com.outsera.producers.domain;

import com.outsera.core.domain.Entity;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.stream.Stream;

@Getter
public class Producer extends Entity {
    private ProducerID id;
    private String producer;
    private Integer previousWin;
    private Integer followingWin;

    @Builder
    private Producer(ProducerID id, String producer, Integer previousWin, Integer followingWin) {
        this.id = id;
        this.producer = producer;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public static Producer create(String producer) {
        return Producer.builder()
            .id(ProducerID.unique())
            .producer(producer)
            .build();
    }

    public Integer getInterval() {
        if (previousWin == null || followingWin == null) {
            return null;
        }

        return difference(followingWin, previousWin);
    }

    public void win(final Integer yearOfWin) {
        if (this.previousWin == null) {
            this.previousWin = yearOfWin;
            return;
        }

        if (this.followingWin == null && yearOfWin >= this.previousWin) {
            this.followingWin = yearOfWin;
            return;
        }

        if(this.followingWin == null) {
            this.followingWin = this.previousWin;
            this.previousWin = yearOfWin;
            return;
        }

        final var sortedWins = Stream.of(this.previousWin, this.followingWin, yearOfWin)
            .sorted(Comparator.naturalOrder())
            .toList();

        for(int i = 0; i < sortedWins.size() - 1; i++) {
            final var currentWin = sortedWins.get(i);
            final var nextWin = sortedWins.get(i + 1);

            if (difference(currentWin, nextWin) < this.getInterval()) {
                this.previousWin = currentWin;
                this.followingWin = nextWin;
            }
        }
    }

    private int difference(int a, int b) {
        return Math.abs(a - b);
    }
}
