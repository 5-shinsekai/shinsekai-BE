package com.example.shinsekai.member.presentation;

import com.example.shinsekai.member.application.MemberService;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.vo.SignUpRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpRequestVo signUpRequestVo) {
        memberService.signUp(SignUpRequestDto.from(signUpRequestVo));
    }
}
