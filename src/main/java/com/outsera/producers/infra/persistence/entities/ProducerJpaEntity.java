package com.outsera.producers.infra.persistence.entities;

import com.outsera.producers.domain.Producer;
import com.outsera.producers.domain.ProducerID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "producers")
public class ProducerJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "producer_name")
    private String producer;

    @Column(name = "interval_win")
    private Integer interval;

    @Column(name = "previous_win")
    private Integer previousWin;

    @Column(name = "following_win")
    private Integer followingWin;

    public static ProducerJpaEntity from(final Producer producer) {
        if (producer == null) {
            throw new IllegalArgumentException("Producer cannot be null");
        }

        ProducerJpaEntity entity = new ProducerJpaEntity();
        entity.setId(producer.getId().getValue());
        entity.setProducer(producer.getProducer());
        entity.setInterval(producer.getInterval());
        entity.setPreviousWin(producer.getPreviousWin());
        entity.setFollowingWin(producer.getFollowingWin());

        return entity;
    }

    public static ProducerJpaEntity from(final ProducerID producerId) {
        if (producerId == null) {
            throw new IllegalArgumentException("ProducerID cannot be null");
        }

        ProducerJpaEntity entity = new ProducerJpaEntity();
        entity.setId(producerId.getValue());
        return entity;
    }

    public Producer toDomain() {
        return Producer.builder()
                .id(ProducerID.from(id))
                .producer(producer)
                .previousWin(previousWin)
                .followingWin(followingWin)
                .build();
    }
}
