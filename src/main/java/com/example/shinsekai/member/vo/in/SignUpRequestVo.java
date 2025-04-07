package com.example.shinsekai.member.vo.in;

import com.example.shinsekai.member.entity.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequestVo {

    private String loginId;
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private Gender gender;
    private String name;
    private LocalDate birth;
}

