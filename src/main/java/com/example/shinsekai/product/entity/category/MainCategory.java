package com.example.shinsekai.product.entity.category;

import com.example.shinsekai.common.entity.BaseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@NoArgsConstructor
public class MainCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String categoryImage;

    @Column(nullable = false)
    private String categoryImageAltText;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    public MainCategory(Long id, String name, String categoryImage, String categoryImageAltText, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
        this.isDeleted = isDeleted;
    }

    public void setDeleted () {
        this.isDeleted = true;
    }
}
