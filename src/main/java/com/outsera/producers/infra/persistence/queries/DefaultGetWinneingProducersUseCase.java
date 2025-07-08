package com.outsera.producers.infra.persistence.queries;

import com.outsera.producers.application.interval.GetWinneingProducersUseCase;
import com.outsera.producers.application.interval.GetWinningProducersOutput;
import com.outsera.producers.infra.persistence.entities.ProducerJpaEntity;
import com.outsera.producers.infra.persistence.entities.ProducerJpaEntity_;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;

@RequestScoped
@RequiredArgsConstructor
public class DefaultGetWinneingProducersUseCase extends GetWinneingProducersUseCase {

    private final EntityManager entityManager;

    @Override
    public GetWinningProducersOutput execute() {

        final List<ProducerJpaEntity> min = takeTheFirstTwo(
            (builder, root) -> builder.asc(root.get(ProducerJpaEntity_.interval)));
        final List<ProducerJpaEntity> max = takeTheFirstTwo(
            (builder, root) -> builder.desc(root.get(ProducerJpaEntity_.interval)));

        return GetWinningProducersOutput.builder()
            .min(min.stream().map(ProducerJpaEntity::toDomain).toList())
            .max(max.stream().map(ProducerJpaEntity::toDomain).toList())
            .build();
    }

    private List<ProducerJpaEntity> takeTheFirstTwo(BiFunction<CriteriaBuilder, Root<ProducerJpaEntity>, Order> orderBy) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ProducerJpaEntity> query = builder.createQuery(ProducerJpaEntity.class);
        final Root<ProducerJpaEntity> root = query.from(ProducerJpaEntity.class);

        query.select(root)
            .where(builder.isNotNull(root.get(ProducerJpaEntity_.interval)))
            .orderBy(orderBy.apply(builder, root));

        return entityManager.createQuery(query).setMaxResults(1).getResultList();
    }
}
