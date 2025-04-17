package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.common.jwt.TokenType;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.dto.in.SocialMemberUpdateRequestDto;
import com.example.shinsekai.member.dto.out.KakaoTokenResponseDto;
import com.example.shinsekai.member.dto.out.KakaoUserResponseDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Value("${jwt.token.access-expire-time}")
    private long accessExpireTime;

    @Value("${jwt.token.refresh-expire-time}")
    private long refreshExpireTime;

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisProvider redisProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public KakaoTokenResponseDto getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        log.info("headers {}", headers);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponseDto> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                KakaoTokenResponseDto.class
        );

        return response.getBody();
    }

    @Override
    public KakaoUserResponseDto getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserResponseDto> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                KakaoUserResponseDto.class
        );

        return response.getBody();
    }

    @Override
    public SignInResponseDto socialLogin(KakaoUserResponseDto userResponseDto) {
        Member member = memberRepository.findBySocialId(userResponseDto.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_SOCIAL_MEMBER));

        log.info("socialLogin_1");

        String accessToken = jwtTokenProvider.generateToken(TokenType.ACCESS, member);
        String refreshToken = jwtTokenProvider.generateToken(TokenType.REFRESH, member);

        log.info("socialLogin_2");

        redisProvider.setToken(TokenType.ACCESS, member.getMemberUuid(), accessToken, accessExpireTime);
        redisProvider.setToken(TokenType.REFRESH, member.getMemberUuid(), refreshToken, refreshExpireTime);

        log.info("socialLogin_3");

        UserDetails userDetails = userDetailsService.loadUserByUsername(member.getMemberUuid());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

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

        Authentication authentication = jwtTokenProvider.authenticate(member, socialMemberUpdateRequestDto.getPassword());
        if (!authentication.isAuthenticated()) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        // 회원 정보 가져와서 컨텍스트홀더에 저장 -> memberUuid 꺼내기 가능해짐
        UserDetails userDetails = userDetailsService.loadUserByUsername(member.getMemberUuid());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 소셜 계정으로 전환
        member.updateSocialMember(socialMemberUpdateRequestDto.getSocialId());

        return jwtTokenProvider.createToken(member);
    }
}
