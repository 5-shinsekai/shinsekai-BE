package com.example.shinsekai.category.presentation;

import com.example.shinsekai.category.application.FilterServiceImpl;
import com.example.shinsekai.category.vo.out.CategoryFilterResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Filter", description = "Filter 관련 API")
@RequestMapping("api/v1/filter")
@RequiredArgsConstructor
@RestController
public class FilterController {

    private final FilterServiceImpl filterService;

    @Operation(summary = "메인 카테고리 하위 카테고리 필터 조회")
    @GetMapping("/{mainCategoryId}")
    public BaseResponseEntity<CategoryFilterResponseVo> getCategoryFilter(@PathVariable Long mainCategoryId) {
        return new BaseResponseEntity<>(filterService.getCategoryFilter(mainCategoryId).toVo());
    }
}
