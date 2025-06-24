package com.outsera.producers.infra.models;

import com.outsera.producers.domain.Producer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetWinningProducersDetailResponse {
    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

    public static GetWinningProducersDetailResponse from(Producer producer) {
        return GetWinningProducersDetailResponse.builder()
                .producer(producer.getProducer())
                .interval(producer.getInterval())
                .previousWin(producer.getPreviousWin())
                .followingWin(producer.getFollowingWin())
                .build();
    }
}
