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
    private Long productId;

    @Builder
    public ProductEventList(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
    }
}
