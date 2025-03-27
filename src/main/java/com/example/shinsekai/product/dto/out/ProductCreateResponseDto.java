package com.example.shinsekai.product.dto.out;

import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.vo.out.ProductCreateResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponseDto {

    private String productCode;


    public static ProductCreateResponseDto from(Product product) {
        return ProductCreateResponseDto.builder()
                .productCode(product.getProductCode())
                .build();
    }

    public ProductCreateResponseVo toVo() {
        return ProductCreateResponseVo.builder()
                .productCode(productCode)
                .build();
    }
}

