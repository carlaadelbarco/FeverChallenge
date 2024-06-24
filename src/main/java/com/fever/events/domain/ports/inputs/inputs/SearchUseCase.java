package com.fever.events.domain.ports.inputs.inputs;

import com.fever.events.domain.models.EventList;

import java.time.LocalDateTime;

public interface SearchUseCase {

    EventList search(LocalDateTime startDate, LocalDateTime endDate, String sellMode);

}
