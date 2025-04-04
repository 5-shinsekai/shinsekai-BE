package com.example.shinsekai.option.presentation;


import com.example.shinsekai.option.application.ProductOptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ProductOption", description = "상품 옵션 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/{productCode}")
public class ProductOptionController {

    private final ProductOptionService productOptionService;

}
