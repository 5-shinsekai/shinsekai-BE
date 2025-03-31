package com.example.shinsekai.common.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/redis")
public class RedisTestController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/test")
    public String testRedis() {
        redisTemplate.opsForValue().set("testKey", "Hello Redis!");
        String value = redisTemplate.opsForValue().get("testKey");
        return "Redis Value: " + value;
    }
}
