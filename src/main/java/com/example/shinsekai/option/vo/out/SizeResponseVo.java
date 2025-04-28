package com.example.shinsekai.option.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SizeResponseVo {

    private Long code;
    private String name;

    @Builder
    public SizeResponseVo(Long code, String name) {
        this.code = code;
        this.name = name;
    }
}
