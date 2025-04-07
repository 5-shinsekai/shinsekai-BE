package com.example.shinsekai.member.dto.out;

import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.in.SignInResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInResponseDto {

    private String accessToken;
    private String refreshToken;
    private String memberUuid;
    private String name;

    @Builder
    public SignInResponseDto(String accessToken, String refreshToken , String memberUuid, String name) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberUuid = memberUuid;
        this.name = name;
    }

    public static SignInResponseDto from(Member member, String accessToken, String refreshToken) {
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberUuid(member.getMemberUuid())
                .name(member.getName())
                .build();
    }

    public SignInResponseVo toVo() {
        return SignInResponseVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberUuid(memberUuid)
                .name(name)
                .build();
    }


}
