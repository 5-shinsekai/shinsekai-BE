package com.example.shinsekai.category.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonFilterItemVo {
    private int code;
    private String name;

    @Builder
    public CommonFilterItemVo(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
