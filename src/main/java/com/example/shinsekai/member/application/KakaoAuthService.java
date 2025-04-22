package com.example.shinsekai.member.application;


import com.example.shinsekai.member.dto.in.SocialMemberUpdateRequestDto;
import com.example.shinsekai.member.dto.out.KakaoTokenResponseDto;
import com.example.shinsekai.member.dto.out.KakaoUserResponseDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;


public interface KakaoAuthService {
    KakaoTokenResponseDto getAccessToken(String code);
    KakaoUserResponseDto getUserInfo(String accessToken);
    void socialLogin(KakaoUserResponseDto userResponse, String uuid);
    void registerSocialMember(SocialMemberUpdateRequestDto socialMemberUpdateRequestDto);
    SignInResponseDto loginAndUpdateSocialMember(SocialMemberUpdateRequestDto socialMemberUpdateRequestDto);
}