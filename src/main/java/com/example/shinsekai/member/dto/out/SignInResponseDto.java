package com.example.shinsekai.member.dto.out;

import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.out.SignInResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInResponseDto {

    private String accessToken;
    private String refreshToken;
    private String memberUuid;

    @Builder
    public SignInResponseDto(String accessToken, String refreshToken, String memberUuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberUuid = memberUuid;
    }

    public static SignInResponseDto of(Member member, String accessToken, String refreshToken) {
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberUuid(member.getMemberUuid())
                .build();
    }

    public SignInResponseVo toVo() {
        return SignInResponseVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberUuid(memberUuid)
                .build();
    }


}
