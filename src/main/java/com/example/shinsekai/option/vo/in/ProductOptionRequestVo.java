package com.example.shinsekai.option.vo.in;

import com.example.shinsekai.option.entity.OptionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductOptionRequestVo {

    @NotNull
    private String productCode;
    private Long sizeId;
    private Long colorId;
    private OptionStatus optionStatus;
    private int optionPrice;
    private int stockCount;
    private int minStockCount;
}
