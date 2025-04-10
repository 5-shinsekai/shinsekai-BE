package com.example.shinsekai.category.dto.out;

import com.example.shinsekai.category.dto.in.ProductCategoryListGetRequestDto;
import com.example.shinsekai.category.entity.ProductCategoryList;
import com.example.shinsekai.category.vo.out.ProductCategoryListGetResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListGetResponseDto {
    private Long id;
    private String productCode;
    private Long mainCategoryId;
    private Long subCateogryId;

    @Builder
    public ProductCategoryListGetResponseDto(Long id, String productCode, Long mainCategoryId, Long subCateogryId) {
        this.id = id;
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCateogryId = subCateogryId;
    }

    public static ProductCategoryListGetResponseDto from(ProductCategoryList productCategoryList){
        return ProductCategoryListGetResponseDto.builder()
                .id(productCategoryList.getId())
                .productCode(productCategoryList.getProductCode())
                .mainCategoryId(productCategoryList.getMainCategoryId())
                .subCateogryId(productCategoryList.getSubCategoryId())
                .build();
    }

    public ProductCategoryListGetResponseVo toVo(){
        return ProductCategoryListGetResponseVo.builder()
                .id(id)
                .productCode(productCode)
                .mainCategoryId(mainCategoryId)
                .subCateogryId(subCateogryId)
                .build();
    }

}
