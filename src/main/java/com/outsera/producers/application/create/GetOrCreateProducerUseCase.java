package com.outsera.producers.application.create;

import com.outsera.producers.domain.Producer;
import com.outsera.producers.domain.ProducerID;
import com.outsera.producers.domain.ProducerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetOrCreateProducerUseCase {

    private final ProducerRepository repository;

    public ProducerID execute(final String producerName) {
        return repository.findByName(producerName).map(Producer::getId).orElseGet(() -> {
            final var producer = Producer.create(producerName);
            repository.create(producer);
            return producer.getId();
        });
    }
}
