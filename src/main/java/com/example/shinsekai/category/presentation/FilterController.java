package com.example.shinsekai.category.presentation;

import com.example.shinsekai.category.application.FilterServiceImpl;
import com.example.shinsekai.category.vo.out.CategoryFilterResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Filter", description = "Filter 관련 API")
@RequestMapping("api/v1/filter")
@RequiredArgsConstructor
@RestController
public class FilterController {

    private final FilterServiceImpl filterService;

    @Operation(summary = "메인 카테고리 하위 카테고리 필터 조회")
    @GetMapping
    public BaseResponseEntity<CategoryFilterResponseVo> getCategoryFilter(
            @RequestParam(required = false) Long mainCategoryId) {
        return new BaseResponseEntity<>(filterService.getCategoryFilter(mainCategoryId).toVo());
    }
}
