package com.example.shinsekai.event.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String eventImage;

    @Column(nullable = false)
    private String eventImageAltText;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Long categoryId;

    @Builder
    public Event(int id, String eventName, String eventImage, String eventImageAltText, LocalDate startDate, LocalDate endDate, Long categoryId) {
        this.id = id;
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
    }
}
