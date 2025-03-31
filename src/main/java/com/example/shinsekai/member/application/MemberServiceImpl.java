package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        try {
            memberRepository.save(signUpRequestDto.toEntity(new BCryptPasswordEncoder()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
        }
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Member member = memberRepository.findByLoginId(signInRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        try {
            Authentication authentication = authenticate(member, signInRequestDto.getPassword());

            return createToken(authentication, member, signInRequestDto);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    // 로그아웃된 Access Token을 Redis에 저장 (블랙리스트 처리)
    @Override
    public void logout(String accessToken, long expirationTime) {
        redisTemplate.opsForValue().set(accessToken, "logout", expirationTime, TimeUnit.MILLISECONDS);
    }

    // 토큰이 블랙리스트인지 확인
    @Override
    public boolean isTokenBlacklisted(String accessToken) {
        return redisTemplate.hasKey(accessToken);
    }

    @Override
    public UserDetails loadUserByUsername (String memberUuid) {
        return memberRepository.findByMemberUuid(memberUuid).orElseThrow(() -> new IllegalArgumentException(memberUuid));
    }

    private SignInResponseDto createToken(Authentication authentication, Member member, SignInRequestDto signInRequestDto) {
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        // 기존 Refresh Token 삭제
        redisTemplate.delete(signInRequestDto.getLoginId());

        // Redis에 Refresh Token 저장 (key: loginId, value: refreshToken)
        redisTemplate.opsForValue().set(
                signInRequestDto.getLoginId(),
                refreshToken,
                jwtTokenProvider.getRefreshTokenExpireTime(),
                TimeUnit.MILLISECONDS
        );

        return SignInResponseDto.from(member, accessToken, refreshToken);
    }


    private Authentication authenticate(Member member, String inputPassword) {

        if (!passwordEncoder.matches(inputPassword, member.getPassword())) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                member.getLoginId(),
                inputPassword
        ));
    }

}
