package com.example.shinsekai.common.email.presentation;

import com.example.shinsekai.common.email.EmailEnum;
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

    @Operation(summary = "아이디 찾기를 위한 인증 메일 발송")
    @PostMapping("/requestId")
    public BaseResponseEntity<Void> sendVerificationCodeForId(@RequestBody EmailVerificationVo emailVerificationVo) {
        emailService.sendVerificationEmail(EmailVerificationRequestDto.from(emailVerificationVo), EmailEnum.FIND_LOGIN_ID);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "비밀번호 변경을 위한 인증 메일 발송")
    @PostMapping("/requestPw")
    public BaseResponseEntity<Void> sendVerificationCodeForPw(@RequestBody EmailVerificationVo emailVerificationVo) {
        emailService.sendVerificationEmail(EmailVerificationRequestDto.from(emailVerificationVo), EmailEnum.CHANGE_PASSWORD);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "인증 메일 검증")
    @PostMapping("/submit")
    public BaseResponseEntity<Void> verifyCode(@RequestBody VerificationCodeVo verificationCodeVo) {
        emailService.verifyCode(VerificationCodeRequestDto.from(verificationCodeVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }


}
