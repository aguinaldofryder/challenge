package com.outsera.movies.infra.persistence;

import com.outsera.movies.domain.Movie;
import com.outsera.producers.infra.persistence.entities.ProducerJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class MovieJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "year_movie")
    private Integer year;

    @Column(name = "title")
    private String title;

    @Column(name = "studios")
    private String studios;

    @Column(name = "winner")
    private Boolean winner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {@JoinColumn(name = "movie_id")}, inverseJoinColumns = {@JoinColumn(name = "producer_id")})
    private List<ProducerJpaEntity> producers;

    public static MovieJpaEntity from(final Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }

        MovieJpaEntity entity = new MovieJpaEntity();
        entity.setId(movie.getId().getValue());
        entity.setYear(movie.getYear());
        entity.setTitle(movie.getTitle());
        entity.setStudios(movie.getStudios());
        entity.setWinner(movie.getWinner());
        entity.setProducers(movie.getProducers().stream().map(ProducerJpaEntity::from).toList());

        return entity;
    }
}
