package com.example.shinsekai.batch.bestProduct.presentation;

import com.example.shinsekai.batch.bestProduct.application.BestProductService;
import com.example.shinsekai.batch.bestProduct.dto.out.BestProductResponseDto;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/best-products")
@Tag(name = "BestProduct", description = "카테고리별 베스트 상품 API")
public class BestProductController {

    private final BestProductService bestProductService;

    @Operation(summary = "카테고리별 베스트 상품 조회", description = "카테고리별로 상품을 반환합니다.")
    @GetMapping
    public BaseResponseEntity<List<BestProductResponseDto>> getBestProductsByCategory(
            @RequestParam Long mainCategoryId
    ) {
        return new BaseResponseEntity<>(bestProductService.getProductRankByMainCategory(mainCategoryId));
    }
}
