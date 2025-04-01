package com.example.shinsekai.common.jwt;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.access-expire-time}")
    private long expireTime;

    @Value("${jwt.token.refresh-expire-time}")
    private long refreshExpireTime;

    /**
     * 토큰에서 uuid 가져오기
     * @param token
     * @return jwt토큰에서 추출한 사용자 UUID 반환
     * @throws IllegalArgumentException
     */
    public String validateAndGetUserUuid(String token) throws IllegalArgumentException {
        try {

            if(isBlacklisted(token)) {
                throw new IllegalArgumentException("로그아웃된 계정입니다.");
            }

            String uuid = extractAllClaims(token).get("uuid", String.class);
            if (uuid == null) {
                throw new IllegalArgumentException("토큰에 담긴 유저 정보가 없습니다.");
            }
            return uuid;
        } catch (JwtException | IllegalArgumentException e) {
            // JWT 파싱이나 서명 오류시 처리
            throw new IllegalArgumentException("잘못된 토큰입니다. 오류: " + e.getMessage());
        }
    }


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
     * 액세스 토큰 생성
     * @param authentication
     * @return 액세스 토큰
     */
    public String generateAccessToken(Authentication authentication, Member member) {

        String memberUuid = member.getMemberUuid(); // UUID 가져오기
        String loginId = authentication.getName(); // 로그인 아이디 가져오기

        Claims claims = Jwts.claims().subject(loginId).build(); // "sub"에 로그인 아이디 저장
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .signWith(getSignKey())
                .claim("uuid", memberUuid)  // "uuid" 키에 UUID 저장
                .claim("username", loginId) // "username" 키에 로그인 아이디 저장
                .issuedAt(now)                 // 토큰 발급 시간 설정
                .expiration(expiration)        // 토큰 만료 시간
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     * @return 리프레시 토큰
     */
    public String generateRefreshToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshExpireTime);

        return Jwts.builder()
                .signWith(getSignKey())
                .issuedAt(now)
                .expiration(expiration)
                .compact();
    }

    public long getExpirationMillis(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.getTime() - System.currentTimeMillis(); // 남은 시간 (밀리초)
    }

    public SignInResponseDto createToken(Authentication authentication, Member member, SignInRequestDto signInRequestDto) {
        String accessToken = generateAccessToken(authentication, member);
        String refreshToken = generateRefreshToken();

        // 기존 Refresh Token 삭제
        boolean deleteSuccess = redisTemplate.delete(signInRequestDto.getLoginId());

        if(!deleteSuccess) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }else {
            // Redis에 Refresh Token 저장 (key: loginId, value: refreshToken)
            redisTemplate.opsForValue().set(
                    signInRequestDto.getLoginId(),
                    refreshToken,
                    getExpirationMillis(refreshToken),
                    TimeUnit.MILLISECONDS
            );
            return SignInResponseDto.from(member, accessToken, refreshToken);
        }

    }

    public Authentication authenticate(Member member, String inputPassword) {
        if (!passwordEncoder.matches(inputPassword, member.getPassword())) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                member.getLoginId(),
                inputPassword
        ));
    }

    public void addBlackList (String accessToken, Long expirationTime) {
        redisTemplate.opsForValue().set("blacklist:" + accessToken
                                        , "true"
                                        , expirationTime
                                        , TimeUnit.MILLISECONDS);
    }

    // 토큰이 블랙리스트에 있는지 확인
    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey("blacklist:" + token);
    }

    public Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
