package com.example.shinsekai.product.entity.option;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;


@Entity
public class SizeOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SIZE_NAME", nullable = false)
    private String sizeName;

}
