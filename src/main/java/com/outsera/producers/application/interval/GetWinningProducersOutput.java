package com.outsera.producers.application.interval;

import com.outsera.producers.domain.Producer;
import lombok.Builder;

import java.util.List;

@Builder
public record GetWinningProducersOutput(
    List<Producer> min,
    List<Producer> max
) {
}
