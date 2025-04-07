package com.example.shinsekai.member.dto.out;

import com.example.shinsekai.member.vo.out.FindIdResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.example.shinsekai.member.entity.Member;

@Getter
@ToString
@NoArgsConstructor
public class FindIdResponseDto {
    private String loginId;

    @Builder
    public FindIdResponseDto(String loginId) {
        this.loginId = loginId;
    }

    public static FindIdResponseDto from(Member member) {
        return FindIdResponseDto.builder()
                .loginId(member.getLoginId())
                .build();
    }

    public FindIdResponseVo toVo() {
        return FindIdResponseVo.builder()
                .loginId(loginId)
                .build();
    }

}
