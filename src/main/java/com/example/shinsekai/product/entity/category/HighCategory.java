package com.example.shinsekai.product.entity.category;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "high_category")
@Getter
public class HighCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "high_category_pk")
    private Long id;

    @Column(name = "HIGH_CATEGORY_NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "highCategory", cascade = CascadeType.ALL)
    private List<MiddleCategory> middleCategories = new ArrayList<>();

    @OneToMany(mappedBy = "highCategory", cascade = CascadeType.ALL)
    private List<ProductAndCategory> productAndCategories = new ArrayList<>();
}
