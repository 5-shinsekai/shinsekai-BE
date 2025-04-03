package com.example.shinsekai.member.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.member.application.MemberService;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.vo.in.SignInRequestVo;
import com.example.shinsekai.member.vo.in.SignInResponseVo;
import com.example.shinsekai.member.vo.in.SignUpRequestVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-up")
    public BaseResponseEntity<Void> signUp(@RequestBody SignUpRequestVo signUpRequestVo) {
        memberService.signUp(SignUpRequestDto.from(signUpRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @PostMapping("/sign-in")
    public BaseResponseEntity<SignInResponseVo> signIn(@RequestBody SignInRequestVo signInRequestVo) {
        SignInResponseVo response = memberService.signIn(SignInRequestDto.from(signInRequestVo)).toVo();
        return new BaseResponseEntity<>(response);
    }

    @PostMapping("/logout")
    public BaseResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").replace("Bearer ", "");
        memberService.logout(accessToken);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    // Access + Refresh Token 모두 갱신
    @GetMapping("/refresh")
    public BaseResponseEntity<SignInResponseDto> createRefreshTokens(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization").replace("Bearer ", "");
        SignInResponseDto response = jwtTokenProvider.reCreateTokens(refreshToken);
        return new BaseResponseEntity<>(response);
    }

    @GetMapping("/findId")
    public BaseResponseEntity<Void> findId() {
return null;
    }

}
