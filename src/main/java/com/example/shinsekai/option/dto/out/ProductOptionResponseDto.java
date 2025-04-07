package com.example.shinsekai.option.dto.out;

import com.example.shinsekai.option.entity.OptionStatus;
import com.example.shinsekai.option.entity.ProductOptionList;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionResponseDto {

    private Long id;
    private String productCode;
    private Long sizeId;
    private Long colorId;
    private OptionStatus optionStatus;
    private int optionPrice;
    private int stockCount;
    private int minStockCount;

    public static ProductOptionResponseDto from(ProductOptionList entity) {
        return new ProductOptionResponseDto(
                entity.getId(),
                entity.getProductCode(),
                entity.getColorId(),
                entity.getSizeId(),
                entity.getOptionStatus(),
                entity.getOptionPrice(),
                entity.getStockCount(),
                entity.getMinStockCount()
        );
    }
}