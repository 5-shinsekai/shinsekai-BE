package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.product.application.ProductService;
import com.example.shinsekai.product.dto.in.ProductCreateRequestDto;
import com.example.shinsekai.product.dto.out.ProductCreateResponseDto;
import com.example.shinsekai.product.vo.in.ProductCreateRequestVo;
import com.example.shinsekai.product.vo.out.ProductCreateResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public BaseResponseEntity<ProductCreateResponseVo> createProduct(
            @RequestBody ProductCreateRequestVo productCreateRequestVo
    ) {
        return new BaseResponseEntity<>(
                productService.createProduct(ProductCreateRequestDto.from(productCreateRequestVo)).toVo()
        );
    }
}
