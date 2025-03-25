package com.example.shinsekai.product.entity.category;

import com.example.shinsekai.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "product_and_category")
@Getter
public class ProductAndCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_and_category_pk")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_code", referencedColumnName = "product_code")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "high_category_pk")
    private HighCategory highCategory;

    @ManyToOne
    @JoinColumn(name = "middle_category_pk")
    private MiddleCategory middleCategory;
}
