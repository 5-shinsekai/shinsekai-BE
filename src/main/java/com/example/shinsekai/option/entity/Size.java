package com.example.shinsekai.option.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "size")
@Getter
@NoArgsConstructor
@Entity
public class Size extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_name", nullable = false)
    private String sizeName;

    @Builder
    public Size(Long id, String sizeName) {
        this.id = id;
        this.sizeName = sizeName;
    }
}
