package com.example.shinsekai.event.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.event.application.ProductEventServiceImpl;
import com.example.shinsekai.event.dto.in.ProductEventListCreateRequestDto;
import com.example.shinsekai.event.dto.in.ProductEventListUpdateRequestDto;
import com.example.shinsekai.event.dto.out.ProductEventListGetResponseDto;
import com.example.shinsekai.event.vo.in.ProductEventListCreateRequestVo;
import com.example.shinsekai.event.vo.in.ProductEventListUpdateRequestVo;
import com.example.shinsekai.event.vo.out.ProductEventListGetResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductEvent", description = "상품 기획전 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-event")
public class ProductEventListController {

    private final ProductEventServiceImpl productEventService;

    @Operation(summary = "상품 기획전 생성")
    @PostMapping
    public BaseResponseEntity<Void> createProductEventList(
            @Valid @RequestBody ProductEventListCreateRequestVo productEventListCreateRequestVo) {
        productEventService.createProductEventList(
                ProductEventListCreateRequestDto.from(productEventListCreateRequestVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "기획전별 상품 전체 조회")
    @GetMapping("/{seasonId}")
    public BaseResponseEntity<List<ProductEventListGetResponseVo>> getAllProductEventList(
            @PathVariable Integer eventId) {
        return new BaseResponseEntity<>(productEventService.getAllProductEventList(eventId).stream()
                .map(ProductEventListGetResponseDto::toVo).toList());
    }

    @Operation(summary = "상품 기획전 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateProductEventList(
            @Valid @RequestBody ProductEventListUpdateRequestVo productEventListUpdateRequestVo) {
        productEventService.updateProductEventList(
                ProductEventListUpdateRequestDto.from(productEventListUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "상품 기획전 하드 삭제")
    @DeleteMapping("/{id}")
    public BaseResponseEntity<Void> deleteProductEventList(@PathVariable Long id) {
        productEventService.deleteProductEventList(id);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
