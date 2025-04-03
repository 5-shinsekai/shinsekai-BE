package com.example.shinsekai.member.dto.in;

import com.example.shinsekai.member.vo.in.FindIdRequestVo;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class FindIdRequestDto {
    private String email;
    private boolean emailVerified;


    public static FindIdRequestDto from(FindIdRequestVo findIdRequestVo) {
        return FindIdRequestDto.builder()
                .email(findIdRequestVo.getEmail())
                .emailVerified(findIdRequestVo.isEmailVerified())
                .build();
    }

}
