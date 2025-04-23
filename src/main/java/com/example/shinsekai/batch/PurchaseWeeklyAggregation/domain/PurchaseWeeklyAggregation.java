package com.example.shinsekai.batch.PurchaseWeeklyAggregation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseWeeklyAggregation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private String productName;
    private Long mainCategoryId;
    private Long quantity;
    @Setter
    private LocalDate aggregateAtStart;
    @Setter
    private LocalDate aggregateAtEnd;

}
