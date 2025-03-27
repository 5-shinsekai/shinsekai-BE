package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.product.application.ProductService;
import com.example.shinsekai.product.dto.in.ProductCreateRequestDto;
import com.example.shinsekai.product.vo.in.ProductCreateRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    public BaseResponseEntity<Void> createProduct(@RequestBody ProductCreateRequestVo productCreateRequestVo) {
        productService.createProduct(ProductCreateRequestDto.from(productCreateRequestVo));
        return new BaseResponseEntity<>();
    }

    @PutMapping("/{productCode}")
    public BaseResponseEntity<Void> updateProduct(
            @PathVariable String productCode,
            @RequestBody ProductCreateRequestVo productCreateRequestVo) {
        productService.updateProduct(productCode, ProductCreateRequestDto.from(productCreateRequestVo));
        return new BaseResponseEntity<>();
    }
}
