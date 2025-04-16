package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.common.jwt.TokenEnum;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.dto.in.SocialMemberUpdateRequestDto;
import com.example.shinsekai.member.dto.out.KakaoTokenResponse;
import com.example.shinsekai.member.dto.out.KakaoUserResponse;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoAuthServiceImpl implements KakaoAuthService{

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${jwt.token.refresh-expire-time}")
    private long refreshExpireTime;

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisProvider redisProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public KakaoTokenResponse getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                KakaoTokenResponse.class
        );

        return response.getBody();
    }

    @Override
    public KakaoUserResponse getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserResponse> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                KakaoUserResponse.class
        );

        return response.getBody();
    }

    @Override
    public SignInResponseDto socialLogin(KakaoUserResponse userResponse) {
        Member member = memberRepository.findBySocialId(userResponse.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_SOCIAL_MEMBER));

        log.info("socialLogin_1");

        String accessToken = jwtTokenProvider.generateToken(member, TokenEnum.ACCESS);
        String refreshToken = jwtTokenProvider.generateToken(member, TokenEnum.REFRESH);

        log.info("socialLogin_2");

        // redis에는 refreshToken만 저장(accessToken은 만료시간이 짧음)
        redisProvider.setToken(member.getMemberUuid(), refreshToken, refreshExpireTime);

        log.info("socialLogin_3");

        return SignInResponseDto.of(member, accessToken, refreshToken);
    }

    @Override
    @Transactional
    public void updateSocialMember(SocialMemberUpdateRequestDto socialMemberUpdateRequestDto) {
        Member member = memberRepository.findByMemberUuid(jwtTokenProvider.getMemberUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        member.updateSocialMember(socialMemberUpdateRequestDto.getSocialId());
    }

    @Override
    public SignInResponseDto loginAndUpdateSocialMember(SocialMemberUpdateRequestDto socialMemberUpdateRequestDto) {
        Member member = memberRepository.findByLoginId(socialMemberUpdateRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        if (!passwordEncoder.matches(socialMemberUpdateRequestDto.getPassword(), member.getPassword())) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        jwtTokenProvider.authenticate(member, socialMemberUpdateRequestDto.getPassword());
        member.updateSocialMember(socialMemberUpdateRequestDto.getSocialId());

        return jwtTokenProvider.createToken(member);
    }
}
