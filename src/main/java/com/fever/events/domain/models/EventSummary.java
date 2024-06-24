package com.fever.events.domain.models;

import lombok.Data;

import java.util.UUID;

@Data
public class EventSummary {

    private UUID id;

    private String title;

    private String start_date;

    private String start_time;

    private String end_date;

    private String end_time;

    private double min_price;

    private double max_price;

}
