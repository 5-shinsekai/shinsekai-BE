package com.example.shinsekai.season.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductSeasonList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private int seasonId;

    @Builder
    public ProductSeasonList(Long id, String productCode, int seasonId) {
        this.id = id;
        this.productCode = productCode;
        this.seasonId = seasonId;
    }
}