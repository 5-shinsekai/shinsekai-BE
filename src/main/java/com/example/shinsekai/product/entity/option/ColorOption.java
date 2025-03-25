package com.example.shinsekai.product.entity.option;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ColorOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COLOR_NAME", nullable = false)
    private String colorName;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt; // 등록일시

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt; // 수정일시
}
