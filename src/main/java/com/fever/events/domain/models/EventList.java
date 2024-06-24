package com.fever.events.domain.models;

import lombok.Data;

import java.util.List;

@Data
public class EventList {

    private List<EventSummary> events;

}
