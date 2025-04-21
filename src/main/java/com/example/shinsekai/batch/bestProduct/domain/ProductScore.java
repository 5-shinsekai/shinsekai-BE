package com.example.shinsekai.batch.bestProduct.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class ProductScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long mainCategoryId;
    private String productCode;
    private Long totalQuantity;
    private Long totalLike;
    @Setter
    private Long productScore;
    @Setter
    private LocalDate calculationAt;

    @Builder
    public ProductScore(Long mainCategoryId, String productCode, Long totalQuantity, Long totalLike, Long productScore, LocalDate calculationAt) {
        this.mainCategoryId = mainCategoryId;
        this.productCode = productCode;
        this.totalQuantity = totalQuantity;
        this.totalLike = totalLike;
        this.productScore = productScore;
        this.calculationAt = calculationAt;
    }
}
