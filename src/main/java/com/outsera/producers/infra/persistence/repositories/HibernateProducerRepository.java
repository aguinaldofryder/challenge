package com.outsera.producers.infra.persistence.repositories;

import com.outsera.producers.domain.Producer;
import com.outsera.producers.domain.ProducerID;
import com.outsera.producers.domain.ProducerRepository;
import com.outsera.producers.infra.persistence.entities.ProducerJpaEntity;
import com.outsera.producers.infra.persistence.entities.ProducerJpaEntity_;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequestScoped
@Transactional
@RequiredArgsConstructor
public class HibernateProducerRepository implements ProducerRepository {

    private final EntityManager entityManager;

    @Override
    public void create(final Producer producer) {
        entityManager.persist(ProducerJpaEntity.from(producer));
    }

    @Override
    public void update(final Producer producer) {
        entityManager.merge(ProducerJpaEntity.from(producer));
    }

    @Override
    public Optional<Producer> findByName(final String name) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ProducerJpaEntity> query = builder.createQuery(ProducerJpaEntity.class);
        final Root<ProducerJpaEntity> root = query.from(ProducerJpaEntity.class);

        final Predicate eqProducerName = builder.equal(root.get(ProducerJpaEntity_.producer), name);

        query.select(root).where(eqProducerName);

        return entityManager.createQuery(query)
            .getResultStream()
            .findFirst()
            .map(ProducerJpaEntity::toDomain);
    }


    /**
     * Implementa um lock pessimista para evitar que dois processos tentem alterar o mesmo produtor ao mesmo tempo.
     * Isso é importante em cenários onde múltiplas instâncias do serviço estão sendo executadas.
     */
    @Override
    public Optional<Producer> findById(final ProducerID id) {
        return Optional.ofNullable(entityManager.find(ProducerJpaEntity.class,
            id.getValue(),
            LockModeType.PESSIMISTIC_WRITE)).map(ProducerJpaEntity::toDomain);
    }
}
