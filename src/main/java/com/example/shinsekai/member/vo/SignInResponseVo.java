package com.example.shinsekai.member.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInResponseVo {

    private String accessToken;
    private String memberUuid;
    private String name;

    @Builder
    public SignInResponseVo(String accessToken, String memberUuid, String name) {
        this.accessToken = accessToken;
        this.memberUuid = memberUuid;
        this.name = name;
    }

}
