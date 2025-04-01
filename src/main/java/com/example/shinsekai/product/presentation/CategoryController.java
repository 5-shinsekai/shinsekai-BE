package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.product.application.CategoryServiceImpl;
import com.example.shinsekai.product.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.product.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.product.dto.out.MainCategoryResponseDto;
import com.example.shinsekai.product.vo.in.MainCategoryCreateRequestVo;
import com.example.shinsekai.product.vo.in.MainCategoryUpdateRequestVo;
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
}
