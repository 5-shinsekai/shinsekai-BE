package com.example.shinsekai.member.dto.in;

import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.SignUpRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberAddDto {
//    private String name;
//    private String email;
//    private String password;
//    private String phone;
//    private String birth;
//
//    public Member toEntity(String memberUuid) {
//        return Member.builder()
//                .memberUuid(memberUuid)
//                .name(name)
//                .email(email)
//                .password(password)
//                .phone(phone)
//                .birth(LocalDate.parse(birth))
//                .build();
//    }
//
//    public static MemberAddDto from(SignUpRequestVo signUpVo) {
//        return MemberAddDto.builder()
//                .name(signUpVo.getName())
//                .email(signUpVo.getEmail())
//                .password(signUpVo.getPassword())
//                .phone(signUpVo.getPhone())
//                .birth(signUpVo.getBirth())
//                .build();
//    }

}
