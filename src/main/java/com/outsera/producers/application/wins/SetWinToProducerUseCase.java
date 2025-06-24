package com.outsera.producers.application.wins;

import com.outsera.producers.domain.Producer;
import com.outsera.producers.domain.ProducerRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetWinToProducerUseCase {

    private final ProducerRepository repository;

    public void execute(final SetWinToProducerInput input) {
        final Producer producer = repository.findById(input.producerId())
            .orElseThrow(() -> new NotFoundException("Producer not found"));

        producer.win(input.yearOfWin());

        repository.update(producer);
    }
}
