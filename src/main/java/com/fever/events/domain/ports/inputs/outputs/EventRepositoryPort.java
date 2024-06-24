package com.fever.events.domain.ports.inputs.outputs;

import com.fever.events.domain.models.EventList;

import java.time.LocalDateTime;

public interface EventRepositoryPort {

    EventList search(LocalDateTime start, LocalDateTime end, String sellMode);

}
