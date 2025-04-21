/*
package com.example.shinsekai.batch.bestProduct.dto.out;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BestProductResponseDto {

    private String productCode;
    private Integer productRank;

    @Builder
    public BestProductResponseDto(String productCode, Integer productRank) {
        this.productCode = productCode;
        this.productRank = productRank;
    }

    public static BestProductResponseDto from(BestProduct bestProduct) {
        return BestProductResponseDto.builder()
                .productCode(bestProduct.getProductCode())
                .productRank(bestProduct.getProductRank())
                .build();
    }
}
*/
