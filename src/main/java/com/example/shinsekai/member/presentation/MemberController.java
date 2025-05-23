package com.example.shinsekai.member.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.member.application.MemberService;
import com.example.shinsekai.member.dto.in.ChangePasswordRequestDto;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.vo.in.ChangePasswordVo;
import com.example.shinsekai.member.vo.in.SignInRequestVo;
import com.example.shinsekai.member.vo.out.SignInResponseVo;
import com.example.shinsekai.member.vo.in.SignUpRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Member", description = "회원 관련 API")
@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    public BaseResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequestVo signUpRequestVo) {
        log.info("signUpRequestVo {}", signUpRequestVo);
        memberService.signUp(SignUpRequestDto.from(signUpRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    public BaseResponseEntity<SignInResponseVo> signIn(@RequestBody SignInRequestVo signInRequestVo) {
        SignInResponseVo response = memberService.signIn(SignInRequestDto.from(signInRequestVo)).toVo();
        return new BaseResponseEntity<>(response);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public BaseResponseEntity<Void> logout() {
        memberService.logout();
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "재인증")
    @GetMapping("/refresh")
    public BaseResponseEntity<SignInResponseDto> createRefreshTokens(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization").substring(7);
        SignInResponseDto response = jwtTokenProvider.reCreateTokens(refreshToken);
        return new BaseResponseEntity<>(response);
    }

    @Operation(summary = "아이디 중복 체크")
    @GetMapping("/check-id")
    public BaseResponseEntity<Boolean> checkId(@RequestParam String loginId) {
        return new BaseResponseEntity<>(memberService.checkId(loginId));
    }

    @Operation(summary = "아이디 찾기")
    @GetMapping("/find-id")
    public BaseResponseEntity<String> findId(@RequestParam String email, @RequestParam String code) {
        return new BaseResponseEntity<>(memberService.findId(email, code));
    }

    @Operation(summary = "비밀번호 변경")
    @PutMapping("/change-pw")
    public BaseResponseEntity<Void> changePassword(HttpServletRequest request,
                                                   @Valid @RequestBody ChangePasswordVo changePasswordVo) {

        String accessToken = jwtTokenProvider.getAccessToken(request);
        memberService.changePassword(ChangePasswordRequestDto.from(changePasswordVo, accessToken));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
