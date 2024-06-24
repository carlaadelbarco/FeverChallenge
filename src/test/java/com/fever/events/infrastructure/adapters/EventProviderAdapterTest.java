package com.fever.events.infrastructure.adapters;

import com.fever.events.infrastructure.entities.BaseEventEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventProviderAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EventProviderAdapter eventProviderAdapter;

    private String xmlResponse;

    @BeforeEach
    void setUp() {
        xmlResponse = "<response>" +
                "<base_event base_event_id=\"1\" sell_mode=\"mode\" title=\"Title\">" +
                "<event event_id=\"e1\" event_start_date=\"2024-06-23T18:00\" event_end_date=\"2024-06-23T20:00\" sell_from=\"2024-06-22T10:00\" sell_to=\"2024-06-23T19:00\" sold_out=\"false\">" +
                "<zone zone_id=\"z1\" capacity=\"100\" price=\"50.0\" name=\"Zone 1\" numbered=\"true\"/>" +
                "</event>" +
                "</base_event>" +
                "</response>";
    }

//    @Test
//    void testFetchEvents() {
//        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(xmlResponse);
//
//        List<BaseEventEntity> baseEvents = eventProviderAdapter.fetchEvents();
//
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
//        assertEquals(1, baseEvents.size());
//        assertEquals("1", baseEvents.get(0).getBaseEventId());
//        assertEquals("Title", baseEvents.get(0).getTitle());
//        assertEquals(1, baseEvents.get(0).getEvents().getZones().size());
//        assertEquals("z1", baseEvents.get(0).getEvents().getZones().get(0).getZoneId());
//    }

    @Test
    void testParseEvents() {
        List<BaseEventEntity> baseEvents = eventProviderAdapter.parseEvents(xmlResponse);

        assertEquals(1, baseEvents.size());
        assertEquals("1", baseEvents.get(0).getBaseEventId());
        assertEquals("Title", baseEvents.get(0).getTitle());
        assertEquals("e1", baseEvents.get(0).getEvents().getEventId());
        assertEquals(LocalDateTime.parse("2024-06-23T18:00"), baseEvents.get(0).getEvents().getEventStartDate());
        assertEquals(LocalDateTime.parse("2024-06-23T20:00"), baseEvents.get(0).getEvents().getEventEndDate());
        assertEquals(1, baseEvents.get(0).getEvents().getZones().size());
        assertEquals("z1", baseEvents.get(0).getEvents().getZones().get(0).getZoneId());
        assertEquals(100, baseEvents.get(0).getEvents().getZones().get(0).getCapacity());
        assertEquals(50.0, baseEvents.get(0).getEvents().getZones().get(0).getPrice());
        assertEquals("Zone 1", baseEvents.get(0).getEvents().getZones().get(0).getName());
        assertEquals(true, baseEvents.get(0).getEvents().getZones().get(0).isNumbered());
    }
}
