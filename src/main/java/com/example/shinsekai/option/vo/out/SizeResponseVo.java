package com.example.shinsekai.option.vo.out;

import com.example.shinsekai.option.entity.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
