package com.example.shinsekai.member.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.jwt.JwtAuthenticationFilter;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.member.application.MemberService;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.vo.SignInRequestVo;
import com.example.shinsekai.member.vo.SignInResponseVo;
import com.example.shinsekai.member.vo.SignUpRequestVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-up")
    public BaseResponseEntity<Void> signUp(@RequestBody SignUpRequestVo signUpRequestVo) {
        memberService.signUp(SignUpRequestDto.from(signUpRequestVo));
        return new BaseResponseEntity<>();
    }

    @PostMapping("/sign-in")
    public BaseResponseEntity<SignInResponseVo> signIn(@RequestBody SignInRequestVo signInRequestVo) {
        SignInResponseVo response = memberService.signIn(SignInRequestDto.from(signInRequestVo)).toVo();
        return new BaseResponseEntity<>(response);
    }

    @PostMapping("/logout")
    public BaseResponseEntity<Void> logout(HttpServletRequest request) {
        memberService.logout(request.getHeader("Authorization"));
        return new BaseResponseEntity<>();
    }
}
