package com.outsera.producers.domain;

import java.util.Optional;

public interface ProducerRepository {
    void create(Producer producer);
    void update(Producer producer);
    Optional<Producer> findById(ProducerID id);
    Optional<Producer> findByName(String name);
}
