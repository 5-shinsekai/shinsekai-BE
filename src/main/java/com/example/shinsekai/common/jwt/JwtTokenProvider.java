package com.example.shinsekai.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

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
            return extractClaim(token, Claims::getSubject);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("토큰에 담긴 유저 정보가 없습니다");
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
    public String generateAccessToken(Authentication authentication) {

        Claims claims = Jwts.claims().subject(authentication.getName()).build();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTime);// + env.getProperty("JWT.token.access-expire-time", Long.class));
        
        return Jwts.builder()
                .signWith(getSignKey())
                .claim("uuid", claims.getSubject())
                .issuedAt(expiration)
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

    public void logout(String token) {
        // Redis에서 해당 Access Token을 삭제
        redisTemplate.delete(token);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSignKey()) // SecretKey 사용
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserUuidFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey()) // SecretKey 사용
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public long getRefreshTokenExpireTime() {
        return this.refreshExpireTime;
    }


    public Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
