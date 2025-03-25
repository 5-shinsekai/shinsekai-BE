package com.example.shinsekai.product.entity.category;

import jakarta.persistence.*;

@Entity
public class HighCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HIGH_CATEGORY_NAME", nullable = false)
    private String highCategoryName;

}
