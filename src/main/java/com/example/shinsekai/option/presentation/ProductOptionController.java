package com.example.shinsekai.option.presentation;


import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.ProductOptionService;
import com.example.shinsekai.option.dto.in.ProductOptionRequestDto;
import com.example.shinsekai.option.dto.out.ProductOptionResponseDto;
import com.example.shinsekai.option.vo.in.ProductOptionRequestVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductOption", description = "상품 옵션 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/{productCode}")
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @GetMapping("/options")
    public BaseResponseEntity<List<ProductOptionResponseDto>> getProductOptions(
            @PathVariable String productCode
    ) {
        return new BaseResponseEntity<>(productOptionService.getOptionsByProductCode(productCode));
    }

    @PostMapping("/options")
    public BaseResponseEntity<Void> createOption(@PathVariable String productCode,
                                                 @RequestBody ProductOptionRequestVo vo) {
        productOptionService.createOption(productCode,ProductOptionRequestDto.from(vo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
