package com.outsera.producers.application.wins;

import com.outsera.producers.domain.Producer;
import com.outsera.producers.domain.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SetWinToProducerUseCaseTest {

    @InjectMocks
    private SetWinToProducerUseCase useCase;

    @Mock
    private ProducerRepository repository;

    @Test
    void givenAProducerWin_whenExists_thenUpdateWin() {
        final String producerName = "Jane Doe";
        final Integer yearOfWin = 2024;

        final Producer producer = spy(Producer.create(producerName));
        when(repository.findById(producer.getId())).thenReturn(Optional.of(producer));

        final var input = SetWinToProducerInput.builder()
            .producerId(producer.getId())
            .yearOfWin(yearOfWin)
            .build();

        useCase.execute(input);

        verify(repository, never()).create(any());
        verify(producer, times(1)).win(yearOfWin);
        verify(repository, times(1)).update(argThat(argument -> {
            return Objects.equals(argument.getId(), producer.getId());
        }));
    }
}