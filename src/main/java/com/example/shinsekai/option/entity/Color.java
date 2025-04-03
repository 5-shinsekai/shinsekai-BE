package com.example.shinsekai.option.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "color")
@Getter
@NoArgsConstructor
public class Color extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color_name", nullable = false)
    private String colorName;

    @Builder
    public Color(Long id, String colorName) {
        this.id = id;
        this.colorName = colorName;
    }

    public void updateColorName(String newName) {
        this.colorName = newName;
    }
}
