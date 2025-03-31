package com.example.shinsekai.season.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String seasonName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Builder
    public Season(int id, String seasonName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
