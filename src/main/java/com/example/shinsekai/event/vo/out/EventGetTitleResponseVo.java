package com.example.shinsekai.event.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EventGetTitleResponseVo {
    private Integer code;
    private String name;

    @Builder
    public EventGetTitleResponseVo(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
