package com.example.shinsekai.common.email.presentation;

import com.example.shinsekai.common.email.application.EmailService;
import com.example.shinsekai.common.email.dto.in.EmailVerificationRequestDto;
import com.example.shinsekai.common.email.dto.in.VerificationCodeRequestDto;
import com.example.shinsekai.common.email.vo.EmailVerificationVo;
import com.example.shinsekai.common.email.vo.VerificationCodeVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/emailAuth")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "인증 메일 발송")
    @PostMapping("/request")
    public BaseResponseEntity<Void> sendVerificationCode(@RequestBody EmailVerificationVo emailVerificationVo) {
        emailService.sendVerificationEmail(EmailVerificationRequestDto.from(emailVerificationVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "인증 메일 검증")
    @PostMapping("/submit")
    public BaseResponseEntity<Void> verifyCode(@RequestBody VerificationCodeVo verificationCodeVo) {
        emailService.verifyCode(VerificationCodeRequestDto.from(verificationCodeVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }


}
