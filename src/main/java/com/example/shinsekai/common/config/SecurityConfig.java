package com.example.shinsekai.common.config;

import com.example.shinsekai.common.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //private final AuthenticationProvider oAuthAuthenticationProvider;
    private final AuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(List.of("Authorization, Content-Type, X-CSRF-TOKEN", "Set-Cookie"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(
                                        "/redis/**",
                                        "/api/v1/email/**",
                                        "/api/v1/member/**",
                                        "/api/v1/cart/**",
                                        "/api/v1/size/**",
                                        "/api/v1/color/**",
                                        "/api/v1/product-options/**",
                                        "/api/v1/option/**",
                                        "/api/v1/product/**",
                                        "/api/v1/category/**",
                                        "/api/v1/product-category/**",
                                        "/api/v1/vendor/**",
                                        "/api/v1/starbucks-card/**",
                                        "/api/v1/external/starbucks-card/**",
                                        "/api/v1/purchase/**",
                                        "/api/v1/payment/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/error"
                                )
                                .permitAll()
                               .requestMatchers("/api/v1/review/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/review/**").permitAll()  // GET 요청 허용
                               .requestMatchers(HttpMethod.POST, "/api/v1/review/**").denyAll()   // POST 요청 차단
                               .requestMatchers(HttpMethod.PUT, "/api/v1/review/**").denyAll()    // PUT 요청 차단
                               .requestMatchers(HttpMethod.DELETE, "/api/v1/review/**").denyAll() // DELETE 요청 차단
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(daoAuthenticationProvider)
                //.authenticationProvider(oAuthAuthenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(corsFilter());
        return http.build();
    }
}

