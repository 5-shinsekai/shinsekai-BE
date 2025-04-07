package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.product.application.ProductService;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.vo.in.ProductRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "상품 관련 API")
@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 생성")
    @PostMapping
    public BaseResponseEntity<Void> createProduct(@RequestBody ProductRequestVo productRequestVo) {
        productService.createProduct(ProductRequestDto.from(productRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    /*
    @Operation(summary = "상품 생성")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponseEntity<Void> createProduct(
            @RequestPart("product") ProductRequestVo productVo,
            @RequestPart("thumbnail") MultipartFile thumbnailFile,
            @RequestPart("contentImage") MultipartFile contentImageFile) {

        ProductRequestDto dto = ProductRequestDto.from(productVo);
        productService.createProduct(dto, thumbnailFile, contentImageFile);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
    */

    @Operation(summary = "상품 수정")
    @PutMapping("/{productCode}")
    public BaseResponseEntity<Void> updateProduct(
            @PathVariable String productCode,
            @RequestBody ProductRequestVo productRequestVo) {
        productService.updateProduct(productCode, ProductRequestDto.from(productRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 하드 삭제")
    @DeleteMapping("/{productCode}")
    public BaseResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
        productService.hardDeleteProduct(productCode);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 소트프 삭제")
    @PatchMapping("/soft-delete/{productCode}")
    public BaseResponseEntity<Void> softDeleteProduct(@PathVariable String productCode) {
        productService.softDeleteProduct(productCode);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 상태 토글 (SELLING ↔ HIDDEN) ")
    @PatchMapping("/toggle-productStatus/{productCode}")
    public BaseResponseEntity<Void> toggleProductStatus(@PathVariable String productCode) {
        productService.toggleProductStatus(productCode);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "판매 중인 상품 상세 조회")
    @GetMapping("/{productCode}")
    public BaseResponseEntity<ProductResponseDto> getSellingProduct(@PathVariable String productCode) {
        return new BaseResponseEntity<>(productService.getSellingProduct(productCode));
    }

    @Operation(summary = "판매 중인 상품 전체 조회")
    @GetMapping
    public BaseResponseEntity<List<ProductResponseDto>> getAllSellingProducts() {
        return new BaseResponseEntity<>(productService.getAllSellingProducts());
    }
}
