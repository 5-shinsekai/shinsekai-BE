package com.example.shinsekai.event.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListUpdateRequestVo {
    @NotNull
    private Long id;
    private String productCode;
    private Integer eventId;

    @Builder
    public ProductEventListUpdateRequestVo(Long id, String productCode, Integer eventId) {
        this.id = id;
        this.productCode = productCode;
        this.eventId = eventId;
    }
}
