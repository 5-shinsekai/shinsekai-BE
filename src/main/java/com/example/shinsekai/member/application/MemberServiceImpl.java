package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.common.jwt.TokenEnum;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisProvider redisProvider;

    /**
     * @param signUpRequestDto
     */
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        try {
            memberRepository.save(signUpRequestDto.toEntity(new BCryptPasswordEncoder()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
        }
    }

    /**
     * @param signInRequestDto
     * @return SignInResponseDto
     */
    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Member member = memberRepository.findByLoginId(signInRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        try {
            Authentication authentication = jwtTokenProvider.authenticate(member, signInRequestDto.getPassword());
            return jwtTokenProvider.createToken(authentication, member);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    /**
     * @param accessToken
     */
    @Override
    public void logout(String accessToken) {

        // redis에서 토큰 삭제
        boolean deleteRefreshTokenSuccess
                = redisProvider.deleteValue(jwtTokenProvider.extractAllClaims(accessToken).getSubject());
        if(!deleteRefreshTokenSuccess)
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
    }

    /**
     * @param memberUuid
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername (String memberUuid) {
        return memberRepository.findByMemberUuid(memberUuid).orElseThrow(() -> new IllegalArgumentException(memberUuid));
    }

}
