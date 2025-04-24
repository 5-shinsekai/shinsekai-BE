package com.example.shinsekai.category.dto.in;

import com.example.shinsekai.category.entity.SubCategory;
import com.example.shinsekai.category.vo.in.SubCategoryUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryUpdateRequestDto {
    private Long id;
    private String name;
    private Long mainCategoryId;

    @Builder
    public SubCategoryUpdateRequestDto(Long id, String name, Long mainCategoryId) {
        this.id = id;
        this.name = name;
        this.mainCategoryId = mainCategoryId;
    }

    public static SubCategoryUpdateRequestDto from(
            SubCategoryUpdateRequestVo subCategoryUpdateRequestVo){
        return SubCategoryUpdateRequestDto.builder()
                .id(subCategoryUpdateRequestVo.getCategoryId())
                .name(subCategoryUpdateRequestVo.getName())
                .mainCategoryId(subCategoryUpdateRequestVo.getMainCategoryId())
                .build();
    }

    public SubCategory toEntity(SubCategory subCategory){
        return SubCategory.builder()
                .id(id)
                .name(name == null ? subCategory.getName() : name)
                .mainCategoryId(mainCategoryId == null ? subCategory.getMainCategoryId() : mainCategoryId)
                .build();
    }
}
