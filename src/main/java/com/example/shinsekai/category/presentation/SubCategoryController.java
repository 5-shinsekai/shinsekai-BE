package com.example.shinsekai.category.presentation;

import com.example.shinsekai.category.application.SubCategoryServiceImpl;
import com.example.shinsekai.category.dto.in.SubCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.SubCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.SubCategoryResponseDto;
import com.example.shinsekai.category.vo.in.SubCategoryCreateRequestVo;
import com.example.shinsekai.category.vo.in.SubCategoryUpdateRequestVo;
import com.example.shinsekai.category.vo.out.SubCategoryResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SubCategory", description = "SubCategory 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class SubCategoryController {

    private final SubCategoryServiceImpl subCategoryService;

    @GetMapping("/sub/{categoryId}")
    public BaseResponseEntity<List<SubCategoryResponseVo>> getAllSubCategory(@PathVariable Long categoryId) {
        return new BaseResponseEntity<>(
                subCategoryService.getAllSubCategory(categoryId)
                        .stream()
                        .map(SubCategoryResponseDto::toVo)
                        .toList());
    }

    @PostMapping("/sub")
    public BaseResponseEntity<Boolean> createSubCategory(
            @RequestBody SubCategoryCreateRequestVo subCategoryCreateRequestVo){
        subCategoryService.createSubCategory(SubCategoryCreateRequestDto.from(subCategoryCreateRequestVo));
        return new BaseResponseEntity<>(true);
    }

    @PatchMapping("/sub/soft-delete/{categoryId}")
    public BaseResponseEntity<Boolean> softDeleteSubCategory(@PathVariable Long categoryId){
        subCategoryService.softDeleteSubCategory(categoryId);
        return new BaseResponseEntity<>(true);
    }

    @PutMapping("/sub/{categoryId}")
    public BaseResponseEntity<Boolean> updateSubCategory(
            @PathVariable Long categoryId,
            @RequestBody SubCategoryUpdateRequestVo subCategoryUpdateRequestVo){
        subCategoryService.updateSubCategory(SubCategoryUpdateRequestDto.from(categoryId, subCategoryUpdateRequestVo));
        return new BaseResponseEntity<>(true);
    }
}
