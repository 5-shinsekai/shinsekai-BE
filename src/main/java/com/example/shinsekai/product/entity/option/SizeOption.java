package com.example.shinsekai.product.entity.option;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "size_option")
@Getter
public class SizeOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_name", nullable = false)
    private String sizeName;

}
