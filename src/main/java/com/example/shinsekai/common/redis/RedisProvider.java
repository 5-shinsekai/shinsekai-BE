package com.example.shinsekai.common.redis;

import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    public String getToken(String memberUuid) {
        return redisTemplate.opsForValue().get(memberUuid);
    }

    public void setToken(String memberUuid, String token, long expirationTime) {
        redisTemplate.opsForValue().set(
                memberUuid,
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

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }


    public boolean deleteValue(String key) {
        return redisTemplate.delete(key);
    }


    public void setTemporaryPayment(String orderId, PurchaseTemporaryRequestDto purchaseTemporaryRequestDto, long expirationTime) {
        try {
            String json = objectMapper.writeValueAsString(purchaseTemporaryRequestDto); // 객체 → JSON 변환
            redisTemplate.opsForValue().set(orderId, json, expirationTime, TimeUnit.MILLISECONDS);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize PendingPayment: {}", e.getMessage());
        }
    }

    public String getTemporaryPayment(String orderId) {
        return redisTemplate.opsForValue().get(orderId);
    }
}
