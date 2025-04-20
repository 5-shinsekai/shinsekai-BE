package com.example.shinsekai.common.redis;

import com.example.shinsekai.common.jwt.TokenType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisProvider {

    private final StringRedisTemplate redisTemplate;

    public String getToken(TokenType tokenType, String memberUuid) {
        return redisTemplate.opsForValue().get(tokenType + ":" + memberUuid);
    }

    public void setToken(TokenType tokenType, String memberUuid, String token, long expirationTime) {
        redisTemplate.opsForValue().set(
                tokenType + ":" + memberUuid,
                token,
                expirationTime,
                TimeUnit.MILLISECONDS
        );
    }

    public String getEmailVerificationCodeForLoginId(String email) {
        return redisTemplate.opsForValue().get("EMAIL_ID:"+email);
    }

    public void setEmailVerificationCodeForLoginId(String email, String eMailVerificationCode, long expirationTime) {
        redisTemplate.opsForValue().set(
                "EMAIL_ID:"+email,
                eMailVerificationCode,
                expirationTime,
                TimeUnit.MILLISECONDS
        );
    }

    public String getEmailVerificationCodeForPw(String email) {
        return redisTemplate.opsForValue().get("EMAIL_PW:"+email);
    }

    public void setEmailVerificationCodeForPw(String email, String eMailVerificationCode, long expirationTime) {
        redisTemplate.opsForValue().set(
                "EMAIL_PW:"+email,
                eMailVerificationCode,
                expirationTime,
                TimeUnit.MILLISECONDS
        );
    }

    public String getEmailVerificationCodeForSignUp(String email) {
        return redisTemplate.opsForValue().get("EMAIL_SU:"+email);
    }

    public void setEmailVerificationCodeForSignUp(String email, String eMailVerificationCode, long expirationTime) {
        redisTemplate.opsForValue().set(
                "EMAIL_SU:"+email,
                eMailVerificationCode,
                expirationTime,
                TimeUnit.MILLISECONDS
        );
    }

    public void setSignInData(String state, String signInData) {
        redisTemplate.opsForValue().set(state, signInData);
    }

    public String getSignInData(String state) {
        return redisTemplate.opsForValue().get(state);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }


    public boolean deleteValue(String key) {
        return redisTemplate.delete(key);
    }

}
