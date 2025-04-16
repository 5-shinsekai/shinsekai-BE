package com.example.shinsekai.like.dto.out;

import com.example.shinsekai.like.entity.ProductLike;
import com.example.shinsekai.like.vo.out.ProductLikeResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductLikeResponseDto {

    private Long productLikeId;
    private String memberUuid;
    private String productCode;

    @Builder
    public ProductLikeResponseDto(Long productLikeId, String memberUuid, String productCode) {
        this.productLikeId = productLikeId;
        this.memberUuid = memberUuid;
        this.productCode = productCode;
    }

    public static ProductLikeResponseDto from(ProductLike productLike) {
        return ProductLikeResponseDto.builder()
                .productLikeId(productLike.getId())
                .memberUuid(productLike.getMemberUuid())
                .productCode(productLike.getProductCode())
                .build();
    }

    public ProductLikeResponseVo toVo() {
        return ProductLikeResponseVo.builder()
                .productLikeId(productLikeId)
                .productCode(productCode)
                .build();
    }
}
