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
     * @param accessToken
     * @return jwt토큰에서 추출한 사용자 UUID 반환
     * @throws IllegalArgumentException
     */
    public String validateAndGetUserUuid(String accessToken) throws IllegalArgumentException {
        try {

            if(!isAccessTokenExists(accessToken)) {
                throw new BaseException(BaseResponseStatus.NO_SIGN_IN);
            }

            String uuid = extractAllClaims(accessToken).get("uuid", String.class);
            if (uuid == null) {
                throw new BaseException(BaseResponseStatus.NO_SIGN_IN);
            }
            return uuid;
        } catch (JwtException | IllegalArgumentException e) {
            // JWT 파싱이나 서명 오류시 처리
            throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
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
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .signWith(getSignKey())
                .subject(loginId)
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

        // Redis에 Access Token 저장 (Key: "ACCESS:" + loginId, Value: accessToken)
        redisTemplate.opsForValue().set(
                "ACCESS:" + signInRequestDto.getLoginId(),
                accessToken,
                getExpirationMillis(accessToken),
                TimeUnit.MILLISECONDS
        );

        // Redis에 Refresh Token 저장 (key: loginId, value: refreshToken)
        redisTemplate.opsForValue().set(
                signInRequestDto.getLoginId(),
                refreshToken,
                getExpirationMillis(refreshToken),
                TimeUnit.MILLISECONDS
        );
        return SignInResponseDto.from(member, accessToken, refreshToken);
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

    public boolean deleteAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        return redisTemplate.delete("ACCESS:" + claims.getSubject());
    }

    public boolean deleteRefreshToken(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        return redisTemplate.delete(claims.getSubject());
    }

    public String getAccessTokenFromRedis(String loginId) {
        // "ACCESS:" + 로그인 ID로 저장된 Access Token 가져오기
        return redisTemplate.opsForValue().get("ACCESS:" + loginId);
    }

    public String getRefreshTokenFromRedis(String loginId) {
        // 로그인 ID를 키로 저장된 Refresh Token 가져오기
        return redisTemplate.opsForValue().get(loginId);
    }

    /**
     * 특정 아이디가 Redis에 존재하는지 확인하는 메서드
     * @param accessToken 검사할 키 (예: 로그인 아이디, 토큰)
     * @return 존재하면 true, 없으면 false
     */
    public boolean isAccessTokenExists(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        // Redis에서 저장된 Access Token 가져오기
        String storedToken = getAccessTokenFromRedis(claims.getSubject());

        return storedToken != null && storedToken.equals(accessToken);
    }


    public Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
