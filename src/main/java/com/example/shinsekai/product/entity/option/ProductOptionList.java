package com.example.shinsekai.product.entity.option;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "product_option_list")
@Getter
public class ProductOptionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_pk")
    private SizeOption sizeOptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_pk")
    private ColorOption colorOptions;


}
