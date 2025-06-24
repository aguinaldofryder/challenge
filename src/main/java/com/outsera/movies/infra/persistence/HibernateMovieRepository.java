package com.outsera.movies.infra.persistence;

import com.outsera.movies.domain.Movie;
import com.outsera.movies.domain.MovieRepository;
import com.outsera.movies.infra.MovieWinnerPublisher;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequestScoped
@Transactional
@RequiredArgsConstructor
public class HibernateMovieRepository implements MovieRepository {

    private final EntityManager entityManager;

    private final MovieWinnerPublisher movieWinnerPublisher;

    @Override
    public void create(final Movie movie) {
        entityManager.persist(MovieJpaEntity.from(movie));
        movie.publishDomainEvents(movieWinnerPublisher);
    }
}
