package com.example.shinsekai.option.dto.in;

import lombok.Getter;

@Getter
public class ProductOptionRequestDto {
    private Long sizeId;
    private Long colorId;
    private int optionPrice;
    private int stockCount;
    private int minStockCount;
}