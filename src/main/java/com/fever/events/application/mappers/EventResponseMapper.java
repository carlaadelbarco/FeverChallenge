package com.fever.events.application.service.mappers;

import com.fever.events.domain.models.EventSummary;
import com.fever.events.infrastructure.entities.BaseEventEntity;
import com.fever.events.infrastructure.entities.EventEntity;
import com.fever.events.infrastructure.entities.ZoneEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EventResponseMapper {

    public EventSummary toEventResponseDTO(BaseEventEntity baseEvent, EventEntity event, List<ZoneEntity> zones) {
        EventSummary dto = new EventSummary();
        setAttributes(baseEvent, event, dto);
        calculateMinMax(zones, dto);

        return dto;
    }

    private static void calculateMinMax(List<ZoneEntity> zones, EventSummary dto) {
        double minPrice = zones.stream().mapToDouble(ZoneEntity::getPrice).min().orElse(0.0);
        double maxPrice = zones.stream().mapToDouble(ZoneEntity::getPrice).max().orElse(0.0);
        dto.setMin_price(minPrice);
        dto.setMax_price(maxPrice);
    }

    private static void setAttributes(BaseEventEntity baseEvent, EventEntity event, EventSummary dto) {
        dto.setId(UUID.randomUUID());
        dto.setTitle(baseEvent.getTitle());
        dto.setStart_date(String.valueOf(event.getEventStartDate().toLocalDate()));
        dto.setStart_time(String.valueOf(event.getEventStartDate().toLocalTime()));
        dto.setEnd_date(String.valueOf(event.getEventEndDate().toLocalDate()));
        dto.setEnd_time(String.valueOf(event.getEventEndDate().toLocalTime()));
    }
}
