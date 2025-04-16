package com.example.shinsekai.member.application;


import com.example.shinsekai.member.dto.in.SocialMemberUpdateRequestDto;
import com.example.shinsekai.member.dto.out.KakaoTokenResponse;
import com.example.shinsekai.member.dto.out.KakaoUserResponse;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.vo.in.SocialMemberUpdateRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public interface KakaoAuthService {
    KakaoTokenResponse getAccessToken(String code);
    KakaoUserResponse getUserInfo(String accessToken);
    SignInResponseDto socialLogin(KakaoUserResponse userResponse);
    void updateSocialMember(SocialMemberUpdateRequestDto socialMemberUpdateRequestDto);
    SignInResponseDto loginAndUpdateSocialMember(SocialMemberUpdateRequestDto socialMemberUpdateRequestDto);
}