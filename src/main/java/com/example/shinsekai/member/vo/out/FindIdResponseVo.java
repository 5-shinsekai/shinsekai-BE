package com.example.shinsekai.member.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class FindIdResponseVo {
    private String loginId;

    @Builder
    public FindIdResponseVo(String loginId) {
        this.loginId = loginId;
    }
}
