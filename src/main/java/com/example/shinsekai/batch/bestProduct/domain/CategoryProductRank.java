package com.example.shinsekai.batch.bestProduct.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProductRank {
    private Long categoryId;
    private List<ProductRank> productRanks;
}
