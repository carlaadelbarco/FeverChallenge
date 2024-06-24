package com.fever.events.application.usecases;

import com.fever.events.domain.models.EventList;
import com.fever.events.domain.ports.inputs.outputs.EventRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchUseCaseImplTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @InjectMocks
    private SearchUseCaseImpl searchUseCaseImpl;

    private EventList eventList;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        eventList = new EventList();
        start = LocalDateTime.of(2024, 6, 1, 0, 0);
        end = LocalDateTime.of(2024, 7, 1, 0, 0);
    }

    @Test
    void testSearch() {
        when(eventRepositoryPort.search(start, end, "online")).thenReturn(eventList);

        EventList result = searchUseCaseImpl.search(start, end, "online");

        assertEquals(eventList, result);
        verify(eventRepositoryPort).search(start, end, "online");
    }
}
