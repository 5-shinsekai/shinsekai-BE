package com.example.shinsekai.common.jwt;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final RedisProvider redisProvider;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.access-expire-time}")
    private long accessExpireTime;

    @Value("${jwt.token.refresh-expire-time}")
    private long refreshExpireTime;


    /**
     * Claims에서 원하는 claim 값 추출
     * @param token
     * @param claimsResolver jwt토큰에서 추출한 정보를 어떻게 처리할지 결정하는 함수
     * @return jwt토큰에서 모든 클레임(클레임은 토큰에 담긴 정보 의미 ) 추출한 다음 claimsResolver함수를 처리해 결과 반환
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 토큰에서 모든 claims 추출
     * @param token
     * @return jwt토큰에서 모든 클레임 추출
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 토큰 생성
     * @param member, tokenType
     * @return 토큰 문자열
     */
    public String generateToken(TokenType tokenType, Member member) {
        String memberUuid = member.getMemberUuid();
        Date now = new Date();
        Date expiration;
        switch (tokenType) {
            case ACCESS -> {
                expiration = new Date(now.getTime() + accessExpireTime);
                break;
            }
            // REFRESH
            default -> {
                expiration = new Date(now.getTime() + refreshExpireTime);
            }
        }

        return Jwts.builder()
                .signWith(getSignKey())
                .subject(memberUuid)
                .issuedAt(now)                 // 토큰 발급 시간 설정
                .expiration(expiration)        // 토큰 만료 시간
                .compact();
    }


    /**
     * 사용자의 UUID와 입력된 비밀번호로 인증을 시도
     * 인증에 성공하면 Authentication 객체를 반환
     *
     * @param member
     * @param inputPassword
     * @return 인증된 Authentication
     */
    public Authentication authenticate(Member member, String inputPassword) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                member.getMemberUuid(),
                inputPassword
        ));
    }

    /**
     * 토큰 발급
     * @param member
     * @return SignInResponseDto
     */
    public SignInResponseDto createToken(Member member) {

        log.info("in JwtTokenProvider_createToken_here_1");

        // 토큰 생성
        String accessToken = generateToken(TokenType.ACCESS, member);
        String refreshToken = generateToken(TokenType.REFRESH, member);

        log.info("JwtTokenProvider_createToken_here_2");

        redisProvider.setToken(TokenType.ACCESS, member.getMemberUuid(), refreshToken, accessExpireTime);
        redisProvider.setToken(TokenType.REFRESH, member.getMemberUuid(), refreshToken, refreshExpireTime);

        log.info("JwtTokenProvider_createToken_here_3");

        return SignInResponseDto.of(member, accessToken, refreshToken);
    }

    /**
     * 토큰 삭제
     * @param tokenType, memberUuid
     * @return 토큰
     */
    public boolean deleteToken(TokenType tokenType, String memberUuid) {
        return redisProvider.deleteValue(tokenType + ":" + memberUuid);
    }

    /**
     * 특정 아이디가 Redis에 존재하는지 확인하는 메서드
     * @param token 검사할 키 (예: 로그인 아이디, 토큰)
     * @return 존재하면 true, 없으면 false
     */
    public boolean isAccessTokenExists(String token) {
        // Redis에서 저장된 Access Token 가져오기
        String storedToken = redisProvider.getToken(TokenType.ACCESS, extractAllClaims(token).getSubject());
        return storedToken != null && storedToken.equals(token);
    }

    /**
     * Access Token, Refresh Token 검증 후 재발급
     * refreshToken은 redis에 저장
     * @param refreshToken
     * @return SignInResponseDto
     */
    @Transactional
    public SignInResponseDto reCreateTokens(String refreshToken) {

        String memberUuid;
        try {
            memberUuid = extractAllClaims(refreshToken).getSubject();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN );
        }
        String storedRefreshToken = redisProvider.getToken(TokenType.REFRESH, memberUuid);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
        }

        // 새 Access Token 생성
        Member member = memberRepository.findByMemberUuid(memberUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        String newAccessToken = generateToken(TokenType.ACCESS, member);
        String newRefreshToken = generateToken(TokenType.REFRESH, member);

        // redis에 저장
        redisProvider.setToken(TokenType.ACCESS, member.getMemberUuid(), newAccessToken, accessExpireTime);
        redisProvider.setToken(TokenType.REFRESH, member.getMemberUuid(), newRefreshToken, refreshExpireTime);

        return SignInResponseDto.of(member, newAccessToken, newRefreshToken);
    }

    /**
     * 토큰이 만료되었는지 확인
     * @param token
     * @return 유효하면 true, 만료되었으면 false
     */
    public boolean isTokenValid(String token) {
        try {
            return !extractAllClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    /**
     * 엑세스 토큰에서 memberUuid꺼내기
     * @param request
     * @return memberUuid
     */
    public String  getAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }

    public String getMemberUuid() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * JWT 서명을 위한 HMAC 키를 생성합니다.
     * secretKey 문자열을 바이트 배열로 변환한 후, HMAC-SHA 알고리즘에 적합한 키로 변환하여 반환합니다.
     * @return JWT 서명에 사용할 Key 객체
     */
    public Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
