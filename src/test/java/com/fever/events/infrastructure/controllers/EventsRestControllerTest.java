package com.fever.events.infrastructure.controllers;

import com.fever.events.application.service.EventsService;
import com.fever.events.domain.models.EventList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EventsRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventsService eventsService;

    @InjectMocks
    private EventsRestController eventsRestController;

    private EventList eventList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventsRestController).build();
        eventList = new EventList();
    }

    @Test
    void testSearchEvents() throws Exception {
        LocalDateTime startsAt = LocalDateTime.of(2024, 6, 1, 0, 0);
        LocalDateTime endsAt = LocalDateTime.of(2024, 7, 1, 0, 0);

        when(eventsService.search(startsAt, endsAt, "online")).thenReturn(eventList);

        mockMvc.perform(get("/search")
                        .param("starts_at", "2024-06-01T00:00:00")
                        .param("ends_at", "2024-07-01T00:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
