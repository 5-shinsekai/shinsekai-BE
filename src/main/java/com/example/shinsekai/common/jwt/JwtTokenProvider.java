package com.example.shinsekai.common.jwt;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import com.example.shinsekai.member.vo.RefreshRequestVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
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
    private final MemberRepository memberRepository;

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

    public long getExpirationMillis(String token, TokenEnum tokenType) {
        // 만료 시간을 명확히 설정해야 하므로 토큰이 전달되면 만료 시간을 구하는 로직을 명시적으로 작성합니다.
        long expirationTime = 0L;

        if ("ACCESS".equals(tokenType.toString())) {
            expirationTime = expireTime;  // 액세스 토큰의 만료 시간 (예: 1시간)
        } else {
            expirationTime = refreshExpireTime;  // 리프레시 토큰의 만료 시간 (예: 7일)
        }

        return expirationTime;
    }

    public long getRemainExpirationMillis(String token, String tokenType) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String loginId = claims.getSubject();

        // Redis에서 저장된 만료 시간 조회 (초 단위)
        String redisKey = tokenType + loginId;
        Long expirationTime = redisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);

        return expirationTime - System.currentTimeMillis(); // 남은 시간 (밀리초)
    }

    public SignInResponseDto createToken(Authentication authentication, Member member, SignInRequestDto signInRequestDto) {
        // 토큰 생성
        String accessToken = generateAccessToken(authentication, member);
        String refreshToken = generateRefreshToken();

        // redis 저장
        setRedisTemplate(signInRequestDto.getLoginId(), accessToken, TokenEnum.ACCESS);
        setRedisTemplate(signInRequestDto.getLoginId(), refreshToken, TokenEnum.REFRESH);

        return SignInResponseDto.from(member, accessToken, refreshToken);
    }

    @Transactional
    public void setRedisTemplate(String loginId, String token, TokenEnum tokenType) {
        String key = "ACCESS".equals(tokenType.toString()) ? "ACCESS:" + loginId : loginId;

        redisTemplate.opsForValue().set(
                key,
                token,
                getExpirationMillis(token, tokenType),
                TimeUnit.MILLISECONDS
        );
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

    public boolean deleteToken(String accessToken, TokenEnum tokenType) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        String type = "ACCESS".equals(tokenType.toString())  ? "ACCESS:" : "";

        return redisTemplate.delete(type + claims.getSubject());
    }

    public String getTokenFromRedis(String loginId, TokenEnum tokenType) {
        String type = "ACCESS".equals(tokenType.toString())  ? "ACCESS:" : "";
        return redisTemplate.opsForValue().get(type + loginId);
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
        String storedToken = getTokenFromRedis(claims.getSubject(), TokenEnum.ACCESS);

        return storedToken != null && storedToken.equals(accessToken);
    }

    /**
     * Access Token 갱신 (Refresh Token 검증 후 재발급)
     * @param refreshRequestVo (loginId)
     * @return SignInResponseDto
     */
    @Transactional
    public SignInResponseDto refreshTokens(RefreshRequestVo refreshRequestVo) {

        String refreshToken = getTokenFromRedis(refreshRequestVo.getLoginId(), TokenEnum.REFRESH);
        String storedRefreshToken = getTokenFromRedis(refreshRequestVo.getLoginId(), TokenEnum.REFRESH);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);
        }

        if (isTokenExpired(refreshToken, TokenEnum.REFRESH)) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);
        }

        // 새 Access Token 생성
        Member member = memberRepository.findByLoginId(refreshRequestVo.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
        Authentication authentication = new UsernamePasswordAuthenticationToken(refreshRequestVo.getLoginId(), null, member.getAuthorities());
        String newAccessToken = generateAccessToken(authentication, member);
        String newRefreshToken = generateRefreshToken();

        // redis에 저장
        setRedisTemplate(refreshRequestVo.getLoginId(), newAccessToken, TokenEnum.ACCESS);
        setRedisTemplate(refreshRequestVo.getLoginId(), newRefreshToken, TokenEnum.REFRESH);

        return SignInResponseDto.from(member, newAccessToken, newRefreshToken);
    }

    /**
     * 토큰이 만료되었는지 확인
     * @param token, tokenType
     * @return 만료되었으면 true, 유효하면 false
     */
    public boolean isTokenExpired(String token, TokenEnum tokenType) {
        try {
            Claims claims = Jwts.parser()           // JWT를 파싱할 수 있는 파서를 생성하는 메서드
                    .setSigningKey(getSignKey())    // JWT의 서명을 검증하기 위해 사용되는 키를 설정하는 메서드
                    .build()                        // Jwts.parser()로 생성한 파서 객체를 설정한 후에, 실제로 파서를 생성하고 사용할 준비를 완료
                    .parseClaimsJws(token)          // 실제로 JWT 토큰을 파싱하는 메서드
                    .getBody();                     // JWT에서 실제 데이터인 클레임(Claims)을 추출

            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
