package com.example.shinsekai.batch.bestProduct.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BestProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private Long mainCategoryId;
    private Integer productRank;
    @Comment("랭킹 날짜")
    private LocalDate rankAt;


}