package com.example.shinsekai.product.entity.category;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long mainCategoryId;
}
