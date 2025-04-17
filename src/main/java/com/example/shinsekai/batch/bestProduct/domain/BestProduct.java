package com.example.shinsekai.batch.bestProduct.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class BestProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private Long mainCategoryId;
    private Integer quantity;
    private Integer productRank;
    private LocalDate orderDate;
}