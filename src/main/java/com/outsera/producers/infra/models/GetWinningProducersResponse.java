package com.outsera.producers.infra.models;

import com.outsera.producers.application.interval.GetWinningProducersOutput;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetWinningProducersResponse {
    private final List<GetWinningProducersDetailResponse> min;
    private final List<GetWinningProducersDetailResponse> max;

    public static GetWinningProducersResponse from(final GetWinningProducersOutput output) {
        return GetWinningProducersResponse.builder()
            .min(output.min().stream().map(GetWinningProducersDetailResponse::from).toList())
            .max(output.max().stream().map(GetWinningProducersDetailResponse::from).toList())
            .build();
    }
}
