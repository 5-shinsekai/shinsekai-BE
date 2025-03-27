package com.example.shinsekai.product.entity.category;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "sub_category")
@Getter
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_category_pk", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_pk")
    private MainCategory mainCategory;
}
