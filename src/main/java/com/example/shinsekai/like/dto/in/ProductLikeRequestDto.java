package com.example.shinsekai.like.dto.in;

import com.example.shinsekai.like.entity.ProductLike;
import com.example.shinsekai.like.vo.in.ProductLikeRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductLikeRequestDto {

    private Long productLikeId;
    private String memberUuid;
    private String productCode;
    private Boolean isLiked;

    @Builder
    public ProductLikeRequestDto(Long productLikeId,
                                 String memberUuid,
                                 String productCode,
                                 Boolean isLiked) {
        this.productLikeId = productLikeId;
        this.memberUuid = memberUuid;
        this.productCode = productCode;
        this.isLiked = isLiked;
    }

    public static ProductLikeRequestDto of(String memberUuid, ProductLikeRequestVo productLikeRequestVo) {
        return ProductLikeRequestDto.builder()
                .productLikeId(productLikeRequestVo.getProductLikeId())
                .memberUuid(memberUuid)
                .productCode(productLikeRequestVo.getProductCode())
                .isLiked(productLikeRequestVo.getIsLiked())
                .build();
    }

    public ProductLike toEntity() {
        return ProductLike.builder()
                .id(productLikeId)
                .memberUuid(memberUuid)
                .productCode(productCode)
                .build();
    }
}
