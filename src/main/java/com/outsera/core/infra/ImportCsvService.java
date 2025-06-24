package com.outsera.core.infra;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.outsera.movies.application.CreateMovieInput;
import com.outsera.movies.application.CreateMovieUseCase;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.*;

@ApplicationScoped
@RequiredArgsConstructor
public class ImportCsvService {

    @ConfigProperty(name = "csv.file.path", defaultValue = "./data/movielist.csv")
    private String csvFilePath;

    private final CreateMovieUseCase createMovieUseCase;

    void onStart(@Observes StartupEvent event) {
        try (InputStream is = new FileInputStream(csvFilePath)) {
            try (InputStreamReader reader = new InputStreamReader(is)) {

                CsvToBean<MovieBean> beans = new CsvToBeanBuilder<MovieBean>(reader).withType(
                    MovieBean.class).withSeparator(';').withIgnoreEmptyLine(true).build();

                beans.stream()
                    .map(bean -> CreateMovieInput.builder()
                        .year(bean.getYear())
                        .title(bean.getTitle())
                        .studios(bean.getStudios())
                        .winner(bean.getWinner())
                        .producers(bean.getProducers())
                        .build())
                    .forEach(createMovieUseCase::execute);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo CSV: " + csvFilePath, e);
        }
    }
}
