package com.fever.events.application.mappers;

import com.fever.events.application.service.mappers.EventResponseMapper;
import com.fever.events.domain.models.EventSummary;
import com.fever.events.infrastructure.entities.BaseEventEntity;
import com.fever.events.infrastructure.entities.EventEntity;
import com.fever.events.infrastructure.entities.ZoneEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventResponseMapperTest {

    @InjectMocks
    private EventResponseMapper eventResponseMapper;

    @Mock
    private BaseEventEntity baseEvent;

    @Mock
    private EventEntity event;

    @Mock
    private ZoneEntity zone1;

    @Mock
    private ZoneEntity zone2;

    private List<ZoneEntity> zones;

    @BeforeEach
    void setUp() {
//        when(baseEvent.getTitle()).thenReturn("Event Title");
//
//        LocalDateTime eventStartDate = LocalDateTime.of(2024, 6, 23, 18, 0);
//        when(event.getEventStartDate()).thenReturn(eventStartDate);
//        LocalDateTime sellFrom = LocalDateTime.of(2024, 6, 22, 10, 0);
//        when(event.getSellFrom()).thenReturn(sellFrom);
//        LocalDateTime sellTo = LocalDateTime.of(2024, 6, 23, 20, 0);
//        when(event.getSellTo()).thenReturn(sellTo);
//
//        when(zone1.getPrice()).thenReturn(50.0);
//        when(zone2.getPrice()).thenReturn(100.0);
//        zones = Arrays.asList(zone1, zone2);
        lenient().when(baseEvent.getTitle()).thenReturn("Event Title");

        LocalDateTime eventStartDate = LocalDateTime.of(2024, 6, 23, 18, 0);
        lenient().when(event.getEventStartDate()).thenReturn(eventStartDate);
        LocalDateTime eventEndDate = LocalDateTime.of(2024, 6, 23, 20, 0);
        lenient().when(event.getEventEndDate()).thenReturn(eventEndDate);
        LocalDateTime sellFrom = LocalDateTime.of(2024, 6, 22, 10, 0);
        lenient().when(event.getSellFrom()).thenReturn(sellFrom);
        LocalDateTime sellTo = LocalDateTime.of(2024, 6, 23, 20, 0);
        lenient().when(event.getSellTo()).thenReturn(sellTo);

        lenient().when(zone1.getPrice()).thenReturn(50.0);
        lenient().when(zone2.getPrice()).thenReturn(100.0);
        zones = Arrays.asList(zone1, zone2);
    }

    @Test
    void testToEventResponseDTO() {
        EventSummary dto = eventResponseMapper.toEventResponseDTO(baseEvent, event, zones);

        assertNotNull(dto.getId());
        assertEquals("Event Title", dto.getTitle());
        assertEquals("2024-06-23", dto.getStart_date());
        assertEquals("18:00", dto.getStart_time());
        assertEquals("2024-06-23", dto.getEnd_date());
        assertEquals("20:00", dto.getEnd_time());
        assertEquals(50.0, dto.getMin_price());
        assertEquals(100.0, dto.getMax_price());
    }

    @Test
    void testToEventResponseDTOWithEmptyZones() {
        List<ZoneEntity> emptyZones = List.of();
        EventSummary dto = eventResponseMapper.toEventResponseDTO(baseEvent, event, emptyZones);

        assertNotNull(dto.getId());
        assertEquals("Event Title", dto.getTitle());
        assertEquals("2024-06-23", dto.getStart_date());
        assertEquals("18:00", dto.getStart_time());
        assertEquals("2024-06-23", dto.getEnd_date());
        assertEquals("20:00", dto.getEnd_time());
        assertEquals(0.0, dto.getMin_price());
        assertEquals(0.0, dto.getMax_price());
    }
}
