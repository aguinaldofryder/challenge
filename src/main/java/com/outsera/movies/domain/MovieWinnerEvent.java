package com.outsera.movies.domain;

import com.outsera.core.domain.DomainEvent;
import com.outsera.producers.domain.ProducerID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieWinnerEvent extends DomainEvent {
    private final ProducerID producerId;
    private final Integer yearOfWin;
}
