package com.example.shinsekai.event.dto.in;

import com.example.shinsekai.event.entity.ProductEventList;
import com.example.shinsekai.event.vo.in.ProductEventListUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListUpdateRequestDto {
    private Long id;
    private String productCode;
    private Integer eventId;

    @Builder
    public ProductEventListUpdateRequestDto(Long id, String productCode, Integer eventId) {
        this.id = id;
        this.productCode = productCode;
        this.eventId = eventId;
    }

    public static ProductEventListUpdateRequestDto from(ProductEventListUpdateRequestVo vo) {
        return ProductEventListUpdateRequestDto.builder()
                .id(vo.getId())
                .productCode(vo.getProductCode())
                .eventId(vo.getEventId())
                .build();
    }

    public ProductEventList toEntity(ProductEventList productEventList) {
        return ProductEventList.builder()
                .id(id)
                .productCode(productCode == null ? productEventList.getProductCode() : productCode)
                .eventId(eventId == null ? productEventList.getEventId() : eventId)
                .build();
    }
}
