package com.outsera.producers.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ProducerTest {

    @Test
    void givenAValidData_whenCallCreate_thenReturnProducer() {
        ProducerID expectedId = ProducerID.unique();
        String expectedProducerName = "John Doe";

        try (MockedStatic<ProducerID> mockedId = Mockito.mockStatic(ProducerID.class)) {
            mockedId.when(ProducerID::unique).thenReturn(expectedId);

            Producer producer = Producer.create(expectedProducerName);

            assertNotNull(producer);
            assertEquals(expectedId, producer.getId());
            assertEquals(expectedProducerName, producer.getProducer());
            assertNull(producer.getPreviousWin());
            assertNull(producer.getFollowingWin());
            assertNull(producer.getInterval());
        }
    }

    @Test
    void givenAProducerWithoutWin_whenCallWin_thenSetPreviousWinAndIntervalShouldBeNull() {
        Producer producer = Producer.builder().id(ProducerID.unique()).producer("Jane Doe").build();

        Integer expectedPreviousWin = 2020;

        Integer yearOfWin = 2020;

        producer.win(yearOfWin);

        assertEquals(expectedPreviousWin, producer.getPreviousWin());
        assertNull(producer.getInterval());
    }

    @Test
    void givenAProducerWithPreviousWins_whenCallWinWithHigherYear_thenSetFollowingWinAndCalculateInterval() {
        final Integer expectedPreviousWin = 2020;
        final Integer expectedFollowingWin = 2022;
        final Integer expectedInterval = expectedFollowingWin - expectedPreviousWin;

        Integer previousWin = 2020;
        Integer yearOfWin = 2022;

        Producer producer = Producer.builder()
            .id(ProducerID.unique())
            .producer("Jane Doe")
            .previousWin(previousWin)
            .build();


        producer.win(yearOfWin);

        assertEquals(expectedPreviousWin, producer.getPreviousWin());
        assertEquals(expectedFollowingWin, producer.getFollowingWin());
        assertEquals(expectedInterval, producer.getInterval());
    }

    @Test
    void givenAProducerWithPreviousWins_whenCallWinWithLowerYear_thenSetFollowingWinAndIntervalShouldBeNull() {
        final Integer expectedPreviousWin = 2002;
        final Integer expectedFollowingWin = 2018;
        final Integer expectedInterval = expectedFollowingWin - expectedPreviousWin;

        final Integer actualPreviousWin = 2018;
        final Integer actualFollowWin = null;

        Producer producer = Producer.builder()
            .id(ProducerID.unique())
            .producer("Jane Doe")
            .previousWin(actualPreviousWin)
            .followingWin(actualFollowWin)
            .build();

        final Integer yearOfWin = 2002;

        producer.win(yearOfWin);

        assertEquals(expectedPreviousWin, producer.getPreviousWin());
        assertEquals(expectedFollowingWin, producer.getFollowingWin());
        assertEquals(expectedInterval, producer.getInterval());
    }

    @ParameterizedTest
    @CsvSource({
        "2010, 2015, 2012, 2010, 2012",
        "2010, 2015, 2020, 2010, 2015",
        "2010, 2015, 2019, 2015, 2019",
        "2010, 2015, 2009, 2009, 2010",
        "2010, 2015, 2000, 2010, 2015",
    })
    void givenAProducerWithBothWins_whenCallWin_thenUpdatePreviousAndFollowingWins(
        final Integer actualPreviousWin,
        final Integer actualFollowWin,
        final Integer yearOfWin,
        final Integer expectedPreviousWin,
        final Integer expectedFollowingWin
    ) {
        final Integer expectedInterval = expectedFollowingWin - expectedPreviousWin;


        Producer producer = Producer.builder()
            .id(ProducerID.unique())
            .producer("Jane Doe")
            .previousWin(actualPreviousWin)
            .followingWin(actualFollowWin)
            .build();

        producer.win(yearOfWin);

        assertEquals(expectedPreviousWin, producer.getPreviousWin());
        assertEquals(expectedFollowingWin, producer.getFollowingWin());
        assertEquals(expectedInterval, producer.getInterval());
    }
}