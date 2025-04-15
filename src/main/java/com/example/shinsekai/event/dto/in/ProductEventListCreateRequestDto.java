package com.example.shinsekai.event.dto.in;

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
}
