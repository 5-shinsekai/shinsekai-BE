package com.example.shinsekai.product.entity.category;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "middle_category")
@Getter
public class MiddleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "middle_category_pk", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_category_pk")
    private HighCategory highCategory;
}
