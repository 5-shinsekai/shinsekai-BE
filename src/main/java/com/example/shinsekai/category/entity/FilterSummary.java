package com.example.shinsekai.category.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FilterSummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long mainCategoryId;

    @Column(nullable = false, length = 20)
    private String filterType;

    @Column(nullable = false)
    private Long filterId;

    @Column(nullable = false, length = 20)
    private String filterValue;

    @Builder
    public FilterSummary(Long id, Long mainCategoryId, String filterType, Long filterId, String filterValue) {
        this.id = id;
        this.mainCategoryId = mainCategoryId;
        this.filterType = filterType;
        this.filterId = filterId;
        this.filterValue = filterValue;
    }
}
