package com.example.shinsekai.event.dto.in;

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
}
