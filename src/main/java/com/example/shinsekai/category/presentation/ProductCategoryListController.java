package com.example.shinsekai.category.presentation;

import com.example.shinsekai.category.application.ProductCategoryListServiceImpl;
import com.example.shinsekai.category.dto.in.*;
import com.example.shinsekai.category.vo.in.ProductCategoryListCreateRequestVo;
import com.example.shinsekai.category.vo.in.ProductCategoryListUpdateRequestVo;
import com.example.shinsekai.category.vo.out.ProductCategoryListGetResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductCategoryList", description = "상품 카테고리 관련 API")
@RequestMapping("api/v1/product-category")
@RequiredArgsConstructor
@RestController
public class ProductCategoryListController {

    private final ProductCategoryListServiceImpl productCategoryListService;

    @Operation(summary = "상품 카테고리 생성")
    @PostMapping
    public BaseResponseEntity<Void> createProductCategoryList(
            @RequestBody ProductCategoryListCreateRequestVo productCategoryListCreateRequestVo) {
        productCategoryListService.createProductCategoryList(
                ProductCategoryListCreateRequestDto.from(productCategoryListCreateRequestVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 카테고리 단일 조회")
    @GetMapping("/id/{id}")
    public BaseResponseEntity<ProductCategoryListGetResponseVo> getProductCategoryList(@PathVariable Long id) {
        return new BaseResponseEntity<>(productCategoryListService.getProductCategoryList(
                ProductCategoryListGetRequestDto.from(id)).toVo());
    }

    @Operation(summary = "상품 카테고리 상품코드에 따른 단일 조회")
    @GetMapping("/product-code/{productCode}")
    public BaseResponseEntity<ProductCategoryListGetResponseVo> getProductCategoryListByProductCode(
            @PathVariable String productCode) {
        return new BaseResponseEntity<>(productCategoryListService
                .getProductCategoryListByProductCode(
                        ProductCategoryListGetByProductCodeRequestDto.from(productCode)).toVo()
        );
    }

    @Operation(summary = "상품 카테고리 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateProductCategoryList(
            @RequestBody ProductCategoryListUpdateRequestVo productCategoryListUpdateRequestVo) {
        productCategoryListService.updateProductCategoryList(
                ProductCategoryListUpdateRequestDto.from(productCategoryListUpdateRequestVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 카테고리 단일 삭제")
    @DeleteMapping("/{id}")
    public BaseResponseEntity<Void> deleteProductCategoryList(@PathVariable Long id) {
        productCategoryListService.deleteProductCategoryList(
                ProductCategoryListDeleteRequestDto.from(id));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 카테고리 전체 삭제")
    @DeleteMapping
    public BaseResponseEntity<Void> deleteAllProductCategoryList(
            @RequestBody List<Long> ids) {
        productCategoryListService.deleteAllProductCategoryList(ids.stream()
                .map(ProductCategoryListDeleteRequestDto::from).toList());

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
