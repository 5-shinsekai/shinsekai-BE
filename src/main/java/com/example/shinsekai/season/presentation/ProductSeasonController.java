package com.example.shinsekai.season.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.season.application.ProductSeasonServiceImpl;
import com.example.shinsekai.season.dto.in.ProductSeasonListCreateRequestDto;
import com.example.shinsekai.season.dto.in.ProductSeasonListUpdateRequestDto;
import com.example.shinsekai.season.dto.out.ProductSeasonListGetResponseDto;
import com.example.shinsekai.season.vo.in.ProductSeasonListCreateRequestVo;
import com.example.shinsekai.season.vo.in.ProductSeasonListUpdateRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductSeason", description = "상품 시즌 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-season")
public class ProductSeasonController {

    private final ProductSeasonServiceImpl productSeasonService;

    @Operation(summary = "상품 시즌 생성")
    @PostMapping
    public BaseResponseEntity<Void> createProductSeasonList(
            @Valid @RequestBody ProductSeasonListCreateRequestVo productSeasonListCreateRequestVo) {
        productSeasonService.createProductSeasonList(
                ProductSeasonListCreateRequestDto.from(productSeasonListCreateRequestVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "시즌별 상품 전체 조회")
    @GetMapping("/{seasonId}")
    public BaseResponseEntity<List<String>> getAllProductSeasonList(
            @PathVariable Integer seasonId) {
        return new BaseResponseEntity<>(productSeasonService.getAllProductSeasonList(seasonId).stream()
                .map(ProductSeasonListGetResponseDto::getProductCode).toList());
    }

    @Operation(summary = "상품 시즌 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateProductSeasonList(
            @Valid @RequestBody ProductSeasonListUpdateRequestVo productSeasonListUpdateRequestVo) {
        productSeasonService.updateProductSeasonList(
                ProductSeasonListUpdateRequestDto.from(productSeasonListUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 시즌 하드 삭제")
    @DeleteMapping("/{id}")
    public BaseResponseEntity<Void> deleteProductSeasonList(@PathVariable Long id) {
        productSeasonService.deleteProductSeasonList(id);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
