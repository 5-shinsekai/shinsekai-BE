package com.example.shinsekai.category.presentation;

import com.example.shinsekai.category.application.SubCategoryServiceImpl;
import com.example.shinsekai.category.dto.in.SubCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.SubCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.SubCategoryResponseDto;
import com.example.shinsekai.category.vo.in.SubCategoryCreateRequestVo;
import com.example.shinsekai.category.vo.in.SubCategoryUpdateRequestVo;
import com.example.shinsekai.category.vo.out.SubCategoryResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SubCategory", description = "SubCategory 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category/sub")
public class SubCategoryController {

    private final SubCategoryServiceImpl subCategoryService;

    @Operation(summary = "전체 하위(sub) 카테고리 조회")
    @GetMapping("/{mainCategoryId}")
    public BaseResponseEntity<List<SubCategoryResponseVo>> getAllSubCategory(@PathVariable Long mainCategoryId) {
        return new BaseResponseEntity<>(
                subCategoryService.getAllSubCategory(mainCategoryId)
                        .stream()
                        .map(SubCategoryResponseDto::toVo)
                        .toList());
    }

    @Operation(summary = "하위(sub) 카테고리 생성")
    @PostMapping
    public BaseResponseEntity<Void> createSubCategory(
            @RequestBody SubCategoryCreateRequestVo subCategoryCreateRequestVo) {
        subCategoryService.createSubCategory(SubCategoryCreateRequestDto.from(subCategoryCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "하위(sub) 카테고리 삭제")
    @DeleteMapping("/{categoryId}")
    public BaseResponseEntity<Void> deleteSubCategory(@PathVariable Long categoryId) {
        subCategoryService.deleteSubCategory(categoryId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "하위(sub) 카테고리 수정")
    @PutMapping("/{categoryId}")
    public BaseResponseEntity<Void> updateSubCategory(
            @PathVariable Long categoryId,
            @RequestBody SubCategoryUpdateRequestVo subCategoryUpdateRequestVo) {
        subCategoryService.updateSubCategory(SubCategoryUpdateRequestDto.from(categoryId, subCategoryUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
