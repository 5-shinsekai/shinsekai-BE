package com.example.shinsekai.like.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberUuid;

    @Column(nullable = false)
    private String productCode;

    @Builder
    public ProductLike(Long id, String memberUuid, String productCode) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.productCode = productCode;
    }
}
