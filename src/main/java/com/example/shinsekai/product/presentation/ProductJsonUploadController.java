package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.product.application.ProductJsonUploadService;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.mapper.JsonProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Product", description = "상품 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductJsonUploadController {

    private final ProductJsonUploadService productJsonUploadService;


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
