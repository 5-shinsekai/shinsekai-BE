package com.example.shinsekai.option.entity;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
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


    public void decreaseStock(int quantity) {
        if (this.stockCount < quantity) {
            throw new BaseException(BaseResponseStatus.NOT_ENOUGH_STOCK);
        }
        this.stockCount -= quantity;
        recalculateStatus();
    }

    public void increaseStock(int quantity) {
        this.stockCount += quantity;
        recalculateStatus();
    }

    private void recalculateStatus() {
        if (this.stockCount == 0) {
            this.optionStatus = OptionStatus.OUT_OF_STOCK;
        } else if (this.stockCount <= this.minStockCount) {
            this.optionStatus = OptionStatus.LOW_STOCK;
        } else {
            this.optionStatus = OptionStatus.IN_STOCK;
        }
    }

}
