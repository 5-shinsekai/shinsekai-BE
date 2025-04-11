package com.example.shinsekai.option.presentation;


import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.ProductOptionService;
import com.example.shinsekai.option.dto.in.ProductOptionRequestDto;
import com.example.shinsekai.option.dto.out.ProductOptionResponseDto;
import com.example.shinsekai.option.vo.in.ProductOptionRequestVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
