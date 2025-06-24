package com.outsera.producers.infra;

import com.outsera.producers.application.create.GetOrCreateProducerUseCase;
import com.outsera.producers.application.wins.SetWinToProducerUseCase;
import com.outsera.producers.domain.ProducerRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import lombok.RequiredArgsConstructor;

@Dependent
@RequiredArgsConstructor
public class ProducerCDIConfig {

    private final ProducerRepository producerRepository;

    @Produces
    @RequestScoped
    public GetOrCreateProducerUseCase getOrCreateProducerUseCase() {
        return new GetOrCreateProducerUseCase(producerRepository);
    }

    @Produces
    @RequestScoped
    public SetWinToProducerUseCase setWinToProducerUseCase() {
        return new SetWinToProducerUseCase(producerRepository);
    }
}
