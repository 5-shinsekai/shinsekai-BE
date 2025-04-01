package com.example.shinsekai.product.dto.in;

import com.example.shinsekai.product.entity.category.SubCategory;
import com.example.shinsekai.product.vo.in.SubCategoryCreateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryCreateRequestDto {
    private String name;
    private Long mainCategoryId;
    private boolean isDeleted;

    @Builder
    public SubCategoryCreateRequestDto(String name, Long mainCategoryId, boolean isDeleted) {
        this.name = name;
        this.mainCategoryId = mainCategoryId;
        this.isDeleted = isDeleted;
    }

    public static SubCategoryCreateRequestDto from(SubCategoryCreateRequestVo subCategoryCreateRequestVo){
        return SubCategoryCreateRequestDto.builder()
                .name(subCategoryCreateRequestVo.getName())
                .mainCategoryId(subCategoryCreateRequestVo.getMainCategoryId())
                .isDeleted(subCategoryCreateRequestVo.isDeleted())
                .build();
    }

    public SubCategory toEntity(){
        return SubCategory.builder()
                .name(name)
                .mainCategoryId(mainCategoryId)
                .isDeleted(isDeleted)
                .build();
    }
}
