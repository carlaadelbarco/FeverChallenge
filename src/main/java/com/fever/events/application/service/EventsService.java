package com.fever.events.application.service;

import com.fever.events.application.service.mappers.EventResponseMapper;
import com.fever.events.domain.models.EventList;
import com.fever.events.domain.models.EventSummary;
import com.fever.events.domain.ports.inputs.inputs.SearchUseCase;
import com.fever.events.infrastructure.entities.BaseEventEntity;
import com.fever.events.infrastructure.repository.BaseEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventsService implements SearchUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EventsService.class);

    @Autowired
    private BaseEventRepository baseEventRepository;

    @Autowired
    private EventResponseMapper eventMapper;

    public EventList search(LocalDateTime startsAt, LocalDateTime endsAt, String sellMode) {
        logger.info("Starting search for events from {} to {}", startsAt, endsAt);

        EventList eventList = new EventList();
        List<EventSummary> eventSummaries = new ArrayList<>();

        try {
            List<BaseEventEntity> baseEvents = baseEventRepository.findEventsByDateRangeAndSellModeOnline(startsAt, endsAt, "online");
            logger.info("Found {} events in the specified date range.", baseEvents.size());

            for (BaseEventEntity baseEvent : baseEvents) {
                try {
                    EventSummary dto = eventMapper.toEventResponseDTO(baseEvent, baseEvent.getEvents(), baseEvent.getEvents().getZones());
                    eventSummaries.add(dto);
                } catch (Exception e) {
                    logger.error("Error mapping base event to EventSummary DTO for event ID {}", baseEvent.getBaseEventId(), e);
                }
            }

            eventList.setEvents(eventSummaries);
        } catch (Exception e) {
            logger.error("Error during event search from {} to {}", startsAt, endsAt, e);
        }

        logger.info("Search completed with {} events found.", eventSummaries.size());
        return eventList;
    }
}
