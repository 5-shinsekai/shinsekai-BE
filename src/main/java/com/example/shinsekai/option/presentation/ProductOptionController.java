package com.example.shinsekai.option.presentation;


import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.ProductOptionService;
import com.example.shinsekai.option.dto.in.ProductOptionRequestDto;
import com.example.shinsekai.option.dto.out.ProductOptionResponseDto;
import com.example.shinsekai.option.vo.in.ProductOptionRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "ProductOption", description = "상품 옵션 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-options")
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @GetMapping("/{optionId}")
    public BaseResponseEntity<ProductOptionResponseDto> getProductOptions(@PathVariable Long optionId) {
        return new BaseResponseEntity<>(productOptionService.getProductOption(optionId));
    }

    @PostMapping
    public BaseResponseEntity<Void> createOption(@Valid @RequestBody ProductOptionRequestVo vo) {
        productOptionService.createOption(ProductOptionRequestDto.from(vo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @DeleteMapping("/{optionId}")
    public BaseResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        productOptionService.deleteOption(optionId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 옵션 재고 차감")
    @PostMapping("/{optionId}/decrease-stock")
    public BaseResponseEntity<Void> decreaseStock(
            @PathVariable Long optionId,
            @RequestParam int quantity) {
        productOptionService.decreaseOptionStock(optionId, quantity);
        return new BaseResponseEntity<>();
    }

    @Operation(summary = "상품 옵션 재고 증가")
    @PostMapping("/{optionId}/increase-stock")
    public BaseResponseEntity<Void> increaseStock(
            @PathVariable Long optionId,
            @RequestParam int quantity) {
        productOptionService.increaseOptionStock(optionId, quantity);
        return new BaseResponseEntity<>();
    }
}
