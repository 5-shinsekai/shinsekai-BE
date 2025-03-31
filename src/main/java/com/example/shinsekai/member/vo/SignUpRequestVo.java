package com.example.shinsekai.member.vo;

import com.example.shinsekai.member.entity.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequestVo {

    private String loginId;
    private String email;
    private String password;
    private String nickName;
    private boolean isActive;
    private String phone;
    private Gender gender;
    private String name;
    private LocalDate birth;


}

