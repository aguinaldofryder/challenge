package com.outsera.producers.application.wins;

import com.outsera.producers.domain.ProducerID;
import lombok.Builder;

@Builder
public record SetWinToProducerInput(
    ProducerID producerId,
    Integer yearOfWin
) {
}
