package com.outsera.producers.infra.persistence.repositories;

import com.outsera.producers.domain.Producer;
import com.outsera.producers.domain.ProducerID;
import io.quarkus.test.junit.QuarkusTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@RequiredArgsConstructor
class HibernateProducerRepositoryTest {

    private final HibernateProducerRepository repository;

    @Test
    void givenAProducer_whenCallCreate_thenShouldBePersistOnDatabase() {
        final var expectedProducerId = ProducerID.unique();
        final var expectedProducerName = "Test Producer";
        final var expectedPreviousWin = 2000;
        final var expectedFollowingWin = 2020;
        final var expectedInterval = 20;

        final var producer = Producer.builder()
            .id(expectedProducerId)
            .producer(expectedProducerName)
            .previousWin(expectedPreviousWin)
            .followingWin(expectedFollowingWin)
            .build();

        repository.create(producer);

        final var entity = repository.findByName(expectedProducerName).orElse(null);

        assertNotNull(entity);
        assertEquals(expectedProducerId.getValue(), entity.getId().getValue());
        assertEquals(expectedProducerName, entity.getProducer());
        assertEquals(expectedPreviousWin, entity.getPreviousWin());
        assertEquals(expectedFollowingWin, entity.getFollowingWin());
        assertEquals(expectedInterval, entity.getInterval());
    }

    @Test
    void givenAProducer_whenCallUpdate_thenShouldBeUpdatedOnDatabase() {
        final var expectedProducerId = ProducerID.unique();
        final var expectedProducerName = "Test Producer";
        final var expectedPreviousWin = 2018;
        final var expectedFollowingWin = 2020;
        final var expectedInterval = 2;

        final var producer = Producer.builder()
            .id(expectedProducerId)
            .producer(expectedProducerName)
            .previousWin(expectedPreviousWin)
            .followingWin(2024)
            .build();

        repository.create(producer);

        producer.win(expectedFollowingWin);
        repository.update(producer);

        final var updatedEntity = repository.findById(producer.getId()).orElse(null);

        assertNotNull(updatedEntity);
        assertEquals(expectedProducerId.getValue(), updatedEntity.getId().getValue());
        assertEquals(expectedProducerName, updatedEntity.getProducer());
        assertEquals(expectedPreviousWin, updatedEntity.getPreviousWin());
        assertEquals(expectedFollowingWin, updatedEntity.getFollowingWin());
        assertEquals(expectedInterval, updatedEntity.getInterval());
    }
}