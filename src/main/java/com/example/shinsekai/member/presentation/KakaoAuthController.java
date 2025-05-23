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
import com.example.shinsekai.member.vo.out.KakaoAuthUrlVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLDecoder;
import java.util.UUID;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Tag(name = "Kakao", description = "카카오 소셜 로그인관련 API")
@RestController
@RequestMapping("/api/v1/social/kakao")
@RequiredArgsConstructor
public class KakaoAuthController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;


    @Value("${client-url}")
    private String clientBaseUrl;


    private final KakaoAuthService kakaoAuthService;
    private final RedisProvider redisProvider;

    @Operation(summary = "카카오 로그인 요청")
    @GetMapping("/login")
    public  BaseResponseEntity<KakaoAuthUrlVo> requestSocialLogin(@RequestParam String callbackUrl) {
        String rawState = UUID.randomUUID() + "|" + clientBaseUrl + callbackUrl;
        String encodedState = URLEncoder.encode(rawState, StandardCharsets.UTF_8);

        String clientId = this.clientId;
        String redirectUri = this.redirectUri;
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&state=" + encodedState;

        return new BaseResponseEntity<>(KakaoAuthUrlVo.builder().url(kakaoAuthUrl).build());
    }

    @Operation(summary = "카카오 로그인 후처리")
    @GetMapping("/callback")
    public ResponseEntity<String> kakaoCallback(HttpServletRequest request) {

        String rawState = URLDecoder.decode(request.getParameter("state"), StandardCharsets.UTF_8);

        String[] parts = rawState.split("\\|");
        String uuid = parts[0];
        String url = parts[1];

        // 1. 인가 코드로 (카카오)토큰 요청
        KakaoTokenResponseDto tokenResponse = kakaoAuthService.getAccessToken(request.getParameter("code"));

        // 2. 토큰으로 사용자 정보 요청
        KakaoUserResponseDto userResponseDto = kakaoAuthService.getUserInfo(tokenResponse.getAccessToken());

        log.info("userResponseDto {}", userResponseDto);

        // 3. 사용자 인증
        //    (스타벅스) AccessToken&RefreshToken 생성
        //    SecurityContextHolder에 회원 정보 저장, 회원 정보(토큰 포함) Redis에 저장
        String socialId = kakaoAuthService.socialLogin(userResponseDto, uuid);
        String isSuccess = "false";

        if("".equals(socialId)) {
            isSuccess = "true";
        }

        // 클라이언트로 리다이렉트하기 ( + searchParam: state )
        String callbackUrl = url
                + "?uuid=" + uuid
                + "&isSuccess=" + isSuccess
                + "&socialId=" + socialId;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(callbackUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Redirect
    }

    @Operation(summary = "회원 정보 요청")
    @GetMapping("{uuid}")
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
    @PutMapping("/register")

    public BaseResponseEntity<Void> registerSocialMember(
            @RequestBody SocialMemberUpdateRequestVo socialMemberUpdateRequestVo) {

        kakaoAuthService.registerSocialMember(SocialMemberUpdateRequestDto.from(socialMemberUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "로그인 후 카카오 소셜 회원으로 등록")
    @PutMapping("/login-and-register")
    public BaseResponseEntity<SignInResponseDto> loginAndUpdateSocialMember(
            @RequestBody SocialMemberUpdateRequestVo requestVo) {
        SignInResponseDto signInResponseDto
                = kakaoAuthService.loginAndUpdateSocialMember((SocialMemberUpdateRequestDto.from(requestVo)));
        return new BaseResponseEntity<>(signInResponseDto);
    }
}