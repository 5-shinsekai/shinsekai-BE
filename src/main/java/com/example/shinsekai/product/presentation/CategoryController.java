package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.product.application.CategoryServiceImpl;
import com.example.shinsekai.product.dto.in.*;
import com.example.shinsekai.product.dto.out.MainCategoryResponseDto;
import com.example.shinsekai.product.vo.in.MainCategoryCreateRequestVo;
import com.example.shinsekai.product.vo.in.MainCategoryUpdateRequestVo;
import com.example.shinsekai.product.vo.in.SubCategoryCreateRequestVo;
import com.example.shinsekai.product.vo.in.SubCategoryUpdateRequestVo;
import com.example.shinsekai.product.vo.out.CategoryResponseVo;
import com.example.shinsekai.product.vo.out.MainCategoryResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/main")
    public BaseResponseEntity<List<MainCategoryResponseVo>> getAllMainCategory() {
        return new BaseResponseEntity<>(
                categoryService.getAllMainCategory()
                .stream()
                .map(MainCategoryResponseDto::toVo)
                .toList());
    }

    @PostMapping("/main")
    public BaseResponseEntity<Boolean> createMainCategory(@RequestBody MainCategoryCreateRequestVo mainCategoryCreateRequestVo){
        categoryService.createMainCategory(MainCategoryCreateRequestDto.from(mainCategoryCreateRequestVo));
        return new BaseResponseEntity<>(true);
    }

    @PatchMapping("/main/soft-delete/{categoryId}")
    public BaseResponseEntity<Boolean> softDeleteMainCategory(@PathVariable Long categoryId){
        categoryService.softDeleteMainCategory(categoryId);
        return new BaseResponseEntity<>(true);
    }

    @PutMapping("/main")
    public BaseResponseEntity<Boolean> updateMainCategory(
            @RequestBody MainCategoryUpdateRequestVo mainCategoryUpdateRequestVo){
        categoryService.updateMainCategory(MainCategoryUpdateRequestDto.from(mainCategoryUpdateRequestVo));
        return new BaseResponseEntity<>(true);

    }

    @GetMapping("/sub/{categoryId}")
    public BaseResponseEntity<List<CategoryResponseVo>> getAllSubCategory(@PathVariable Long categoryId) {
        return new BaseResponseEntity<>(
                categoryService.getAllSubCategory(categoryId)
                        .stream()
                        .map(CategoryResponseDto::toVo)
                        .toList());
    }

    @PostMapping("/sub")
    public BaseResponseEntity<Boolean> createSubCategory(
            @RequestBody SubCategoryCreateRequestVo subCategoryCreateRequestVo){
        categoryService.createSubCategory(SubCategoryCreateRequestDto.from(subCategoryCreateRequestVo));
        return new BaseResponseEntity<>(true);
    }

    @PatchMapping("/sub/soft-delete/{categoryId}")
    public BaseResponseEntity<Boolean> softDeleteSubCategory(@PathVariable Long categoryId){
        categoryService.softDeleteSubCategory(categoryId);
        return new BaseResponseEntity<>(true);
    }

    @PutMapping("/sub")
    public BaseResponseEntity<Boolean> updateSubCategory(
            @RequestBody SubCategoryUpdateRequestVo subCategoryUpdateRequestVo){
        categoryService.updateSubCategory(SubCategoryUpdateRequestDto.from(subCategoryUpdateRequestVo));
        return new BaseResponseEntity<>(true);

    }
}
