package com.example.shinsekai.category.presentation;

import com.example.shinsekai.category.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.category.application.MainCategoryServiceImpl;
import com.example.shinsekai.category.dto.out.MainCategoryResponseDto;
import com.example.shinsekai.category.vo.in.MainCategoryCreateRequestVo;
import com.example.shinsekai.category.vo.in.MainCategoryUpdateRequestVo;
import com.example.shinsekai.category.vo.out.CategoryFilterResponseVo;
import com.example.shinsekai.category.vo.out.MainCategoryResponseVo;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MainCategory", description = "MainCategory 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class MainCategoryController {

    private final MainCategoryServiceImpl mainCategoryService;

    @GetMapping("/main")
    public BaseResponseEntity<List<MainCategoryResponseVo>> getAllMainCategory() {
        return new BaseResponseEntity<>(
                mainCategoryService.getAllMainCategory()
                .stream()
                .map(MainCategoryResponseDto::toVo)
                .toList());
    }

    @PostMapping("/main")
    public BaseResponseEntity<Void> createMainCategory(@RequestBody MainCategoryCreateRequestVo mainCategoryCreateRequestVo){
        mainCategoryService.createMainCategory(MainCategoryCreateRequestDto.from(mainCategoryCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @PatchMapping("/main/soft-delete/{categoryId}")
    public BaseResponseEntity<Void> softDeleteMainCategory(@PathVariable Long categoryId){
        mainCategoryService.softDeleteMainCategory(categoryId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @PutMapping("/main/{categoryId}")
    public BaseResponseEntity<Void> updateMainCategory(
            @PathVariable Long categoryId,
            @RequestBody MainCategoryUpdateRequestVo mainCategoryUpdateRequestVo){
        mainCategoryService.updateMainCategory(MainCategoryUpdateRequestDto.from(categoryId, mainCategoryUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/{mainCategoryId}/filter")
    public BaseResponseEntity<CategoryFilterResponseVo> getCategoryFilter(@PathVariable Long mainCategoryId){
        return new BaseResponseEntity<>(mainCategoryService.getCategoryFilter(mainCategoryId).toVo());
    }

}
