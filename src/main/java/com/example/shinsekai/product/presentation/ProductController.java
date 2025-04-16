package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.product.application.ProductFilterService;
import com.example.shinsekai.product.application.ProductJsonUploadService;
import com.example.shinsekai.product.application.ProductSearchService;
import com.example.shinsekai.product.application.ProductService;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.mapper.JsonProductMapper;
import com.example.shinsekai.product.vo.in.ProductRequestVo;
import com.example.shinsekai.product.vo.out.ProductOutlineResponseVo;
import com.example.shinsekai.product.vo.out.ProductResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Tag(name = "Product", description = "상품 관련 API")
@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductFilterService productFilterService;
    private final ProductSearchService productSearchService;
    private final ProductJsonUploadService productJsonUploadService;

    @Operation(summary = "상품 생성")
    @PostMapping
    public BaseResponseEntity<Void> createProduct(@RequestBody ProductRequestVo productRequestVo) {
        productService.createProduct(ProductRequestDto.from(productRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

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
    public BaseResponseEntity<ProductResponseVo> getSellingProduct(@PathVariable String productCode) {
        return new BaseResponseEntity<>(productService.getSellingProduct(productCode).toVo());
    }

    @Operation(summary = "전체 판매 중인 상품 코드만 페이징 조회")
    @GetMapping("/product-code/page")
    public BaseResponseEntity<Page<String>> getSellingProductCodes(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new BaseResponseEntity<>(productService.getAllSellingProductCodes(pageable));
    }

    @Operation(summary = "전체 상품 요약 정보 조회")
    @GetMapping("/{productCode}/outline")
    public BaseResponseEntity<ProductOutlineResponseVo> getProductSummary(@PathVariable String productCode) {
        return new BaseResponseEntity<>(productService.getSellingProductOutline(productCode).toVo());
    }

    @Operation(summary = "상품 복합 조건 검색")
    @GetMapping("/filter")
    public BaseResponseEntity<Page<String>> filterProductsByFilters(
            @RequestParam Long mainCategoryId,
            @RequestParam(required = false) List<Long> subCategoryIds,
            @RequestParam(required = false) List<Integer> seasonIds,
            @RequestParam(required = false) List<Long> sizeIds,
            @RequestParam(required = false) String priceRange,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return new BaseResponseEntity<>(productFilterService.filterProducts(
                mainCategoryId, subCategoryIds, seasonIds, sizeIds, priceRange, pageable
        ));
    }

    @Operation(summary = "상품명  검색")
    @GetMapping("/search")
    public BaseResponseEntity<Page<String>> searchProducts(
            @RequestParam String keyword,
            @PageableDefault(size = 10) Pageable pageable) {
        return new BaseResponseEntity<>(productSearchService.searchByKeyword(keyword,pageable));
    }

    @Operation(summary = "JSON 샘플 상품 일괄 업로드 (Postman 용)")
    @PostMapping("/upload-json/create")
    public BaseResponseEntity<Void> uploadJson(@RequestBody List<Map<String, Object>> rawList) {
        for (Map<String, Object> raw : rawList) {
            ProductRequestDto dto = JsonProductMapper.toDto(raw);
            productJsonUploadService.jsonUploadProduct(dto);
        }
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
