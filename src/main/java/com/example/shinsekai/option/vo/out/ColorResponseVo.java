package com.example.shinsekai.option.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ColorResponseVo {
    private Long code;
    private String name;

    @Builder
    public ColorResponseVo(Long code, String name) {
        this.code = code;
        this.name = name;
    }
}
