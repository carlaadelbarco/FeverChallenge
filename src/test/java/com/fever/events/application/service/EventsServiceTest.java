package com.fever.events.application.service;

import com.fever.events.application.service.mappers.EventResponseMapper;
import com.fever.events.domain.models.EventList;
import com.fever.events.domain.models.EventSummary;
import com.fever.events.infrastructure.entities.BaseEventEntity;
import com.fever.events.infrastructure.entities.EventEntity;
import com.fever.events.infrastructure.entities.ZoneEntity;
import com.fever.events.infrastructure.repository.BaseEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventsServiceTest {

    @Mock
    private BaseEventRepository baseEventRepository;

    @Mock
    private EventResponseMapper eventMapper;

    @InjectMocks
    private EventsService eventsService;

    private BaseEventEntity baseEvent;
    private EventEntity eventEntity;
    private ZoneEntity zoneEntity;
    private EventSummary eventSummary;

    @BeforeEach
    void setUp() {
        baseEvent = mock(BaseEventEntity.class);
        eventEntity = mock(EventEntity.class);
        zoneEntity = mock(ZoneEntity.class);
        eventSummary = mock(EventSummary.class);

        lenient().when(baseEvent.getEvents()).thenReturn(eventEntity);
        lenient().when(eventEntity.getZones()).thenReturn(Arrays.asList(zoneEntity));
        lenient().when(eventMapper.toEventResponseDTO(baseEvent, eventEntity, Arrays.asList(zoneEntity))).thenReturn(eventSummary);
    }

    @Test
    void testSearch() {
        LocalDateTime startsAt = LocalDateTime.of(2024, 6, 1, 0, 0);
        LocalDateTime endsAt = LocalDateTime.of(2024, 7, 1, 0, 0);

        when(baseEventRepository.findEventsByDateRangeAndSellModeOnline(startsAt,endsAt, "online")).thenReturn(Arrays.asList(baseEvent));

        EventList eventList = eventsService.search(startsAt, endsAt, "online");

        assertNotNull(eventList);
        assertEquals(1, eventList.getEvents().size());
        assertEquals(eventSummary, eventList.getEvents().get(0));

        verify(baseEventRepository, times(1)).findEventsByDateRangeAndSellModeOnline(startsAt, endsAt, "online");
        verify(eventMapper, times(1)).toEventResponseDTO(baseEvent, eventEntity, Arrays.asList(zoneEntity));
    }

    @Test
    void testSearchEmpty() {
        LocalDateTime startsAt = LocalDateTime.of(2024, 6, 1, 0, 0);
        LocalDateTime endsAt = LocalDateTime.of(2024, 7, 1, 0, 0);

        EventList eventList = eventsService.search(startsAt, endsAt, "online");

        assertNotNull(eventList);

        verify(baseEventRepository, times(1)).findEventsByDateRangeAndSellModeOnline(startsAt, endsAt, "online");

        verify(eventMapper, times(0)).toEventResponseDTO(any(), any(), any());
    }
}
