package com.example.shinsekai.email.vo;

import com.example.shinsekai.email.entity.EmailType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmailVerificationVo {

    private String name;
    private String email;
    private EmailType mailType;
}
