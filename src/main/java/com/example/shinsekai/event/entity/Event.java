package com.example.shinsekai.event.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String eventName;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String eventImage;

    @Column(nullable = false)
    private String eventImageAltText;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Builder
    public Event(int id, String eventName, String eventImage, String eventImageAltText,
                 LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}