package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.product.application.ProductService;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.vo.in.ProductRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    public BaseResponseEntity<Void> createProduct(@RequestBody ProductRequestVo productRequestVo) {
        productService.createProduct(ProductRequestDto.from(productRequestVo));
        return new BaseResponseEntity<>();
    }

    @PutMapping("/{productCode}")
    public BaseResponseEntity<Void> updateProduct(
            @PathVariable String productCode,
            @RequestBody ProductRequestVo productRequestVo) {
        productService.updateProduct(productCode, ProductRequestDto.from(productRequestVo));
        return new BaseResponseEntity<>();
    }

    @DeleteMapping("/{productCode}")
    public BaseResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
        productService.deleteProduct(productCode);
        return new BaseResponseEntity<>();
    }

    @GetMapping("/{productCode}")
    public BaseResponseEntity<ProductResponseDto> getProduct(@PathVariable String productCode) {
        return new BaseResponseEntity<>(productService.getProduct(productCode));
    }

    @GetMapping()
    public BaseResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return new BaseResponseEntity<>(productService.getAllProducts());
    }
}
