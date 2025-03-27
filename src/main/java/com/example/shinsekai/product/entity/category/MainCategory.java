package com.example.shinsekai.product.entity.category;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "main_category")
@Getter
public class MainCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_category_pk")
    private Long id;

    @Column(name = "main_category_name", nullable = false)
    private String name;

}
