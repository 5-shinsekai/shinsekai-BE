package com.example.shinsekai.category.presentation;

import com.example.shinsekai.category.application.MainCategoryServiceImpl;
import com.example.shinsekai.category.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.MainCategoryResponseDto;
import com.example.shinsekai.category.vo.in.MainCategoryCreateRequestVo;
import com.example.shinsekai.category.vo.in.MainCategoryUpdateRequestVo;
import com.example.shinsekai.category.vo.out.MainCategoryResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MainCategory", description = "MainCategory 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category/main")
public class MainCategoryController {

    private final MainCategoryServiceImpl mainCategoryService;

    @Operation(summary = "전체 메인 카테고리 조회")
    @GetMapping
    public BaseResponseEntity<List<MainCategoryResponseVo>> getAllMainCategory() {
        return new BaseResponseEntity<>(
                mainCategoryService.getAllMainCategory()
                        .stream()
                        .map(MainCategoryResponseDto::toVo)
                        .toList());
    }

    @Operation(summary = "메인 카테고리 생성")
    @PostMapping
    public BaseResponseEntity<Void> createMainCategory(@RequestBody MainCategoryCreateRequestVo mainCategoryCreateRequestVo) {
        mainCategoryService.createMainCategory(MainCategoryCreateRequestDto.from(mainCategoryCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "메인 카테고리 삭제")
    @DeleteMapping("/{categoryId}")
    public BaseResponseEntity<Void> deleteMainCategory(@PathVariable Long categoryId) {
        mainCategoryService.deleteMainCategory(categoryId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "메인 카테고리 수정")
    @PutMapping("/{categoryId}")
    public BaseResponseEntity<Void> updateMainCategory(
            @PathVariable Long categoryId,
            @RequestBody MainCategoryUpdateRequestVo mainCategoryUpdateRequestVo) {
        mainCategoryService.updateMainCategory(MainCategoryUpdateRequestDto.from(categoryId, mainCategoryUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
