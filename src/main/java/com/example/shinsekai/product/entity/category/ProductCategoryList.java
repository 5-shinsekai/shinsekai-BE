package com.example.shinsekai.product.entity.category;

import com.example.shinsekai.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "product_category_list")
@Getter
public class ProductCategoryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private MainCategory mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategory subCategory;
}
