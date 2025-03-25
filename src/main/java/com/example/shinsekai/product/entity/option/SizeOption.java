package com.example.shinsekai.product.entity.option;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SizeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SIZE_NAME", nullable = false)
    private String sizeName;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt; // 등록일시

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt; // 등록일시

}
