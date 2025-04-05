package com.example.shinsekai.common.email.vo;

import com.example.shinsekai.common.email.entity.EmailType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmailVerificationVo {

    private String email;
    private EmailType mailType;
}
