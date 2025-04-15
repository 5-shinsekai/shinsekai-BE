package com.example.shinsekai.event.presentation;

import com.example.shinsekai.event.application.ProductEventServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ProductEvent", description = "상품 기획전 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-event")
public class ProductEventListController {

    private final ProductEventServiceImpl productEventService;

}
