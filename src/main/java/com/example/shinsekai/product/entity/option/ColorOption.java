package com.example.shinsekai.product.entity.option;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "color_option")
@Getter
public class ColorOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color_name", nullable = false)
    private String colorName;

}
