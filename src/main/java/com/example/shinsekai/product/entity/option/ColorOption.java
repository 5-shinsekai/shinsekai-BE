package com.example.shinsekai.product.entity.option;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ColorOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COLOR_NAME", nullable = false)
    private String colorName;
}
