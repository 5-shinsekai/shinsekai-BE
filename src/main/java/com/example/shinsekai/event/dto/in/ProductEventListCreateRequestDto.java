package com.example.shinsekai.event.dto.in;

import com.example.shinsekai.event.entity.ProductEventList;
import com.example.shinsekai.event.vo.in.ProductEventListCreateRequestVo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListCreateRequestDto {
    private String productCode;
    private Integer eventId;

    @Builder
    public ProductEventListCreateRequestDto(String productCode, Integer eventId) {
        this.productCode = productCode;
        this.eventId = eventId;
    }

    public static ProductEventListCreateRequestDto from(ProductEventListCreateRequestVo vo) {
        return ProductEventListCreateRequestDto.builder()
                .productCode(vo.getProductCode())
                .eventId(vo.getEventId())
                .build();
    }

    public ProductEventList toEntity() {
        return ProductEventList.builder()
                .productCode(productCode)
                .eventId(eventId)
                .build();
    }
}
