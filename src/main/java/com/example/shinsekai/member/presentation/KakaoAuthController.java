package com.example.shinsekai.member.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.application.KakaoAuthService;
import com.example.shinsekai.member.dto.in.SocialMemberUpdateRequestDto;
import com.example.shinsekai.member.dto.out.KakaoTokenResponseDto;
import com.example.shinsekai.member.dto.out.KakaoUserResponseDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.vo.in.SocialMemberUpdateRequestVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.UUID;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/social")
@RequiredArgsConstructor
public class KakaoAuthController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    private final KakaoAuthService kakaoAuthService;
    private final RedisProvider redisProvider;

    @Operation(summary = "소셜 로그인 요청")
    @GetMapping("/login")
    public ResponseEntity<Void> requestSocialLogin(@RequestParam String callbackUrl) throws UnsupportedEncodingException {

        String rawState = UUID.randomUUID() + "|" + callbackUrl;
        String encodedState = URLEncoder.encode(rawState, StandardCharsets.UTF_8.toString());

        String clientId = this.clientId; // 실제 클라이언트 ID로 대체
        String redirectUri = this.redirectUri;
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&state=" + encodedState;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(kakaoAuthUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Redirect
    }

    @Operation(summary = "카카오 소셜 로그인")
    @GetMapping("/callback")
    public ResponseEntity<Void> kakaoCallback(HttpServletRequest request) throws UnsupportedEncodingException {

        String rawState = URLDecoder.decode(request.getParameter("state"), StandardCharsets.UTF_8.toString());

        String[] parts = rawState.split("\\|");
        String uuid = parts[0];
        String url = parts[1];

        // 1. 인가 코드로 (카카오)토큰 요청
        KakaoTokenResponseDto tokenResponse = kakaoAuthService.getAccessToken(request.getParameter("code"));

        // 2. 토큰으로 사용자 정보 요청
        KakaoUserResponseDto userResponseDto = kakaoAuthService.getUserInfo(tokenResponse.getAccessToken());

        // 3. (스타벅스) 토큰 생성
        SignInResponseDto signInResponseDto = kakaoAuthService.socialLogin(userResponseDto);

        // 생성된 멤버 정보 스트링으로 바꾸기
        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(signInResponseDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 스트링으로 바꿨으면 redis에 저장 key: uuid / value: 스트링으로 바뀐 멤버 정보
        redisProvider.setSignInData(uuid, json);

        // 클라이언트로 리다이렉트하기 ( + searchParam: state )
        String callbackUrl = url
                + "?uuid=" + uuid;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(callbackUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Redirect
    }

    @Operation(summary = "회원 정보 요청")
    @GetMapping("/{uuid}")
    public BaseResponseEntity<JsonNode> reRequest(@PathVariable String uuid) {
        String jsonString = redisProvider.getSignInData(uuid);
        redisProvider.deleteValue(uuid);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonObject;
        try {
            jsonObject = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new BaseResponseEntity<>(jsonObject);
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
