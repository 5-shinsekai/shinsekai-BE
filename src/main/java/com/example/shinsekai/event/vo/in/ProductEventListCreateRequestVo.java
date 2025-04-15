package com.example.shinsekai.event.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListCreateRequestVo {
    @NotNull
    private String productCode;
    @NotNull
    private Integer eventId;

    @Builder
    public ProductEventListCreateRequestVo(String productCode, Integer eventId) {
        this.productCode = productCode;
        this.eventId = eventId;
    }
}
