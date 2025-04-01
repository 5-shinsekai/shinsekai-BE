package com.example.shinsekai.product.entity.category;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class SubCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long mainCategoryId;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    public SubCategory(Long id, String name, Long mainCategoryId, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.mainCategoryId = mainCategoryId;
        this.isDeleted = isDeleted;
    }

    public void setDeleted(){
        this.isDeleted = true;
    }
}
