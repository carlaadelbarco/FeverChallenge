package com.fever.events.application.service;

import com.fever.events.infrastructure.adapters.EventProviderAdapter;
import com.fever.events.infrastructure.entities.BaseEventEntity;
import com.fever.events.infrastructure.repository.BaseEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProviderServiceTest {

    @Mock
    private EventProviderAdapter eventProviderAdapter;

    @Mock
    private BaseEventRepository baseEventRepository;

    @InjectMocks
    private ProviderService providerService;

    private BaseEventEntity baseEvent;

    @BeforeEach
    void setUp() {
        baseEvent = mock(BaseEventEntity.class);
    }

    @Test
    void testFetchAndStoreEvents() {
        when(eventProviderAdapter.fetchEvents()).thenReturn(Arrays.asList(baseEvent));
        when(baseEvent.getBaseEventId()).thenReturn("123");
        when(baseEventRepository.findBaseEventByBaseEventId("123")).thenReturn(Optional.empty());

        providerService.fetchAndStoreEvents();

        verify(eventProviderAdapter, times(1)).fetchEvents();
        verify(baseEventRepository, times(1)).findBaseEventByBaseEventId("123");
        verify(baseEventRepository, times(1)).save(baseEvent);
    }

    @Test
    void testFetchAndStoreEventsExistingEvent() {
        when(eventProviderAdapter.fetchEvents()).thenReturn(Arrays.asList(baseEvent));
        when(baseEvent.getBaseEventId()).thenReturn("123");
        when(baseEventRepository.findBaseEventByBaseEventId("123")).thenReturn(Optional.of(baseEvent));

        providerService.fetchAndStoreEvents();

        verify(eventProviderAdapter, times(1)).fetchEvents();
        verify(baseEventRepository, times(1)).findBaseEventByBaseEventId("123");
        verify(baseEventRepository, times(0)).save(baseEvent);
    }
}
