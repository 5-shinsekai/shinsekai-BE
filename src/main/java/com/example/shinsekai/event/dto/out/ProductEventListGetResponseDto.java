package com.example.shinsekai.event.dto.out;

import com.example.shinsekai.event.entity.ProductEventList;
import com.example.shinsekai.event.vo.out.ProductEventListGetResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListGetResponseDto {
    private Long id;
    private String productCode;

    @Builder
    public ProductEventListGetResponseDto(Long id, String productCode) {
        this.id = id;
        this.productCode = productCode;
    }

    public static ProductEventListGetResponseDto from(ProductEventList productEventList) {
        return ProductEventListGetResponseDto.builder()
                .id(productEventList.getId())
                .productCode(productEventList.getProductCode())
                .build();
    }

    public ProductEventListGetResponseVo toVo(){
        return ProductEventListGetResponseVo.builder()
                .id(id)
                .productCode(productCode)
                .build();
    }
}
