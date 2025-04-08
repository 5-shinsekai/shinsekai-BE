package com.example.shinsekai.payment.infrastructure.toss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "toss")
public class TossProperties {
    private String secretKey;
    private String confirmUrl;
}