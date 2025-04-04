package com.example.shinsekai.option.dto.out;

import com.example.shinsekai.option.entity.OptionStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionResponseDto {
    private Long optionId;
    private String sizeName;
    private String colorName;
    private int optionPrice;
    private int stockCount;
    private OptionStatus optionStatus;
}