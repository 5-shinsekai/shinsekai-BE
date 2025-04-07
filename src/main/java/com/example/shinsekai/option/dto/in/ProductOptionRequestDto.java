package com.example.shinsekai.option.dto.in;

import com.example.shinsekai.option.entity.OptionStatus;
import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.option.vo.in.ProductOptionRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOptionRequestDto {
    private Long sizeId;
    private Long colorId;
    private OptionStatus optionStatus;
    private int optionPrice;
    private int stockCount;
    private int minStockCount;

    @Builder
    public ProductOptionRequestDto(
            Long sizeId,
            Long colorId,
            OptionStatus optionStatus,
            int optionPrice,
            int stockCount,
            int minStockCount) {
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.optionStatus = optionStatus;
        this.optionPrice = optionPrice;
        this.stockCount = stockCount;
        this.minStockCount = minStockCount;
    }

    public ProductOptionList toEntity(String productCode) {
        return ProductOptionList.builder()
                .productCode(productCode)
                .sizeId(this.sizeId)
                .colorId(this.colorId)
                .optionPrice(this.optionPrice)
                .optionStatus(this.optionStatus)
                .stockCount(this.stockCount)
                .minStockCount(this.minStockCount)
                .build();
    }

    public static ProductOptionRequestDto from(ProductOptionRequestVo productOptionRequestVo ) {
        return ProductOptionRequestDto.builder()
                .sizeId(productOptionRequestVo.getSizeId())
                .colorId(productOptionRequestVo.getColorId())
                .optionPrice(productOptionRequestVo.getOptionPrice())
                .optionStatus(productOptionRequestVo.getOptionStatus())
                .stockCount(productOptionRequestVo.getStockCount())
                .minStockCount(productOptionRequestVo.getMinStockCount())
                .build();
    }

}