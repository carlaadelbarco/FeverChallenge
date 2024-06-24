package com.fever.events.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@Data
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "event_start_date")
    private LocalDateTime eventStartDate;

    @Column(name = "event_end_date")
    private LocalDateTime eventEndDate;

    @Column(name = "sell_from")
    private LocalDateTime sellFrom;

    @Column(name = "sell_to")
    private LocalDateTime sellTo;

    @Column(name = "sold_out")
    private boolean soldOut;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_event_id")
    @JsonBackReference
    private BaseEventEntity baseEvent;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ZoneEntity> zones;
}
