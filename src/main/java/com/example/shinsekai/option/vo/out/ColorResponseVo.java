package com.example.shinsekai.option.vo.out;

import com.example.shinsekai.option.entity.Color;
import lombok.AllArgsConstructor;
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
