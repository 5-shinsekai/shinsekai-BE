package com.example.shinsekai.option.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_option_list")
public class ProductOptionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sizeId;

    private Long colorId;

    @Column(name = "product_code", nullable = false, length = 50, updatable = false)
    private String productCode;

    @Column(name = "option_price", nullable = false)
    private int optionPrice;

    @Column(name = "option_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OptionStatus optionStatus;

    @Column(name = "stock_count")
    private int stockCount;

    @Column(name = "min_stock_count", nullable = false)
    private int minStockCount;

    @Builder
    public ProductOptionList(String productCode,
                             Long sizeId,
                             Long colorId,
                             int optionPrice,
                             OptionStatus optionStatus,
                             int stockCount,
                             int minStockCount) {
        this.productCode = productCode;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.optionPrice = optionPrice;
        this.optionStatus = optionStatus;
        this.stockCount = stockCount;
        this.minStockCount = minStockCount;
    }

}
