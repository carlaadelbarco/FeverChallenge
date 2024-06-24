package com.fever.events.infrastructure.controllers;

import com.fever.events.application.service.EventsService;
import com.fever.events.domain.models.EventList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/search")
public class EventsRestController {

    private final EventsService eventService;

    public EventsRestController(EventsService eventService) {
        this.eventService = eventService;
    }


    @Operation(summary = "Lists the available events on a time range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of plans"),
            @ApiResponse(responseCode = "400", description = "The request was not correctly formed (missing required parameters, wrong types...)"),
            @ApiResponse(responseCode = "500", description = "Generic error")
    })
    @GetMapping
    public EventList searchEvents(
            @Parameter(description = "Return only events that starts after this date", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starts_at,
            @Parameter(description = "Return only events that finishes before this date", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ends_at) {

        return eventService.search(starts_at, ends_at, "online");
    }


}
