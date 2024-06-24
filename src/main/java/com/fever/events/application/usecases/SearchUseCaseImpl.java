package com.fever.events.application.usecases;

import com.fever.events.domain.models.EventList;
import com.fever.events.domain.ports.inputs.inputs.SearchUseCase;
import com.fever.events.domain.ports.inputs.outputs.EventRepositoryPort;

import java.time.LocalDateTime;

public class SearchUseCaseImpl implements SearchUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public SearchUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public EventList search(LocalDateTime start, LocalDateTime end, String sellMode) {
        return eventRepositoryPort.search(start, end, sellMode);
    }
}
