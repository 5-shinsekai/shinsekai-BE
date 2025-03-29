package com.example.shinsekai.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    private final Environment env;

    /**
     * TokenProvider
     * 1. 토큰에서 uuid 가져오기
     * 2. Claim's 원하는 claim 값 추출 ( - JWT version 업데이트로 사용하지 않음)
     * 3. 토큰에서 모든 claims 추출 ( - JWT version 업데이트로 사용하지 않음)
     * 4. 액세스 토큰 생성
     * 5. refresh 토큰 생성
     */

    @Value("${jwt.secret-key}")
    private String secretKey;

    /**
     * 1. 토큰에서 uuid 가져오기
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
     * 2. Claims에서 원하는 claim 값 추출
     * @param token
     * @param claimsResolver jwt토큰에서 추출한 정보를 어떻게 처리할지 결정하는 함수
     * @return jwt토큰에서 모든 클레임(클레임은 토큰에 담긴 정보 의미 ) 추출한 다음 claimsResolver함수를 처리해 결과 반환
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 3. 토큰에서 모든 claims 추출
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
     * 4. 액세스 토큰 생성
     * @param authentication
     * @return 액세스 토큰
     */
    public String generateAccessToken(Authentication authentication) {

        Claims claims = Jwts.claims().subject(authentication.getName()).build();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000000000);// + env.getProperty("JWT.token.access-expire-time", Long.class));

        System.out.println("expiration = " + expiration);

        String strKey = Jwts.builder()
                .signWith(getSignKey())
                .claim("uuid", claims.getSubject())
                .issuedAt(expiration)
                .compact();

        System.out.println("strKey = " + strKey);
        
        return strKey;
    }


    public Key getSignKey() {
        System.out.println("secretKey = " + secretKey);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        System.out.println("key = " + key);
        return key;
    }

}
