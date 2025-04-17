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

    @Builder
    public SignInResponseVo(String accessToken, String refreshToken, String memberUuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberUuid = memberUuid;
    }

}
