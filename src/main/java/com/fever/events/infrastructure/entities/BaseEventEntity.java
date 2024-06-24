package com.fever.events.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "base_event")
@Data
public class BaseEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "base_event_id")
    private String baseEventId;

    @Column(name = "sell_mode")
    private String sellMode;

    @Column(name = "title")
    private String title;

    @OneToOne(mappedBy = "baseEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private EventEntity events;

}