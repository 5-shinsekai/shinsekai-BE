package com.example.shinsekai.event.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class ProductEventList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private int eventId;

    @Builder
    public ProductEventList(Long id, String productCode, int eventId) {
        this.id = id;
        this.productCode = productCode;
        this.eventId = eventId;
    }
}
