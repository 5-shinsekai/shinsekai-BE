package com.example.shinsekai.member.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.member.application.KakaoAuthService;
import com.example.shinsekai.member.application.MemberService;
import com.example.shinsekai.member.dto.in.SocialMemberUpdateRequestDto;
import com.example.shinsekai.member.dto.out.KakaoTokenResponseDto;
import com.example.shinsekai.member.dto.out.KakaoUserResponseDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.vo.out.SignInResponseVo;
import com.example.shinsekai.member.vo.in.SocialMemberUpdateRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kakao")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;
    private final MemberService memberService;

    @Operation(summary = "카카오 소셜 로그인")
    @GetMapping("/callback")
    public BaseResponseEntity<SignInResponseVo> kakaoCallback(@RequestParam("code") String code) {
        // 1. 인가 코드로 (카카오)토큰 요청
        KakaoTokenResponseDto tokenResponse = kakaoAuthService.getAccessToken(code);

        // 2. 토큰으로 사용자 정보 요청
        KakaoUserResponseDto userResponseDto = kakaoAuthService.getUserInfo(tokenResponse.getAccessToken());

        // 3. (스타벅스) 토큰 생성
        SignInResponseDto signInResponseDto = kakaoAuthService.socialLogin(userResponseDto);

        return new BaseResponseEntity<>(signInResponseDto.toVo());
    }

    @Operation(summary = "카카오 소셜 회원으로 등록")
    @PutMapping("/update")
    public BaseResponseEntity<Void> updateSocialMember(
            @RequestBody SocialMemberUpdateRequestVo socialMemberUpdateRequestVo) {

        kakaoAuthService.updateSocialMember(SocialMemberUpdateRequestDto.from(socialMemberUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "로그인 후 카카오 소셜 회원으로 등록")
    @PutMapping("/login-and-update")
    public BaseResponseEntity<SignInResponseDto> loginAndUpdateSocialMember(
            @RequestBody SocialMemberUpdateRequestVo requestVo) {
        SignInResponseDto signInResponseDto
                = kakaoAuthService.loginAndUpdateSocialMember((SocialMemberUpdateRequestDto.from(requestVo)));
        return new BaseResponseEntity<>(signInResponseDto);
    }
}
