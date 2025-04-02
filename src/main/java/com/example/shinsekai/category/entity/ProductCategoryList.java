package com.example.shinsekai.category.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ProductCategoryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private MainCategory mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategory subCategory;
}
