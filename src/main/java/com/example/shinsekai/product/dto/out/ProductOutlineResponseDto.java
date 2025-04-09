package com.example.shinsekai.product.dto.out;

import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.vo.out.ProductOutlineResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductOutlineResponseDto {

    private String productCode;
    private String productName;
    private double productPrice;
    private String thumbnailUrl;
    private int discountRate;

    @Builder
    public ProductOutlineResponseDto(String productCode, String productName, double productPrice, String thumbnailUrl, int discountRate) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.thumbnailUrl = thumbnailUrl;
        this.discountRate = discountRate;
    }

    public static ProductOutlineResponseDto from(Product product) {
        return ProductOutlineResponseDto.builder()
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .thumbnailUrl(product.getThumbnailUrl())
                .discountRate(product.getDiscountRate())
                .build();
    }

    public ProductOutlineResponseVo toVo(){
        return ProductOutlineResponseVo.builder()
                .productCode(productCode)
                .productName(productName)
                .productPrice(productPrice)
                .thumbnailUrl(thumbnailUrl)
                .discountRate(discountRate)
                .build();
    }
}
