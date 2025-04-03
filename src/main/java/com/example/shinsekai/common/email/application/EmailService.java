package com.example.shinsekai.common.email.application;

import com.example.shinsekai.common.email.EmailEnum;
import com.example.shinsekai.common.email.dto.in.EmailAuthRequestDto;
import com.example.shinsekai.common.email.vo.EmailAuthVo;

public interface EmailService {

    void sendVerificationEmail(EmailAuthRequestDto emailAuthRequestDto);
}
