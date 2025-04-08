package com.example.shinsekai.email.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private MailSmtpProperties properties = new MailSmtpProperties();

    @Getter
    @Setter
    public static class MailSmtpProperties {
        private boolean auth;
        private boolean starttls;
        private String sslTrust;
    }

    public boolean isAuth() {
        return properties.auth;
    }

    public boolean isStarttls() {
        return properties.starttls;
    }

    public String getSslTrust() {
        return properties.sslTrust;
    }
}
