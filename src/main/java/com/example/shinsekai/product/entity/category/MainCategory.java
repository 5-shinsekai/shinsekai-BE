package com.example.shinsekai.product.entity.category;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
public class MainCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String categoryImage;

    @Column(nullable = false)
    private boolean hasSize;

    @Column(nullable = false)
    private boolean hasColor;

    @Builder
    public MainCategory(Long id, String name, String categoryImage, boolean hasSize, boolean hasColor) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
        this.hasSize = hasSize;
        this.hasColor = hasColor;
    }
}
