package com.example.shinsekai.member.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoAuthUrlVo {

    private String url;

    @Builder
    public KakaoAuthUrlVo(String url) {
        this.url = url;
    }
}
