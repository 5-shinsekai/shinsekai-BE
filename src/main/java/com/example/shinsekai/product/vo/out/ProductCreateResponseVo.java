package com.example.shinsekai.product.vo.out;

import com.example.shinsekai.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateResponseVo {

    private String productCode;

    @Builder
    public ProductCreateResponseVo(String productCode) {
        this.productCode = productCode;
    }
}
