package com.example.shinsekai.member.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInResponseVo {

    private String accessToken;
    private String refreshToken;
    private String memberUuid;
    private String name;

    @Builder
    public SignInResponseVo(String accessToken, String refreshToken, String memberUuid, String name) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberUuid = memberUuid;
        this.name = name;
    }

}
