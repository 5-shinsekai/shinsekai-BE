package com.example.shinsekai.common.jwt;

import com.example.shinsekai.member.application.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final StringRedisTemplate redisTemplate; // ✅ Redis 추가

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String uuid;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.replace("Bearer ", "");
        
        uuid = jwtTokenProvider.validateAndGetUserUuid(jwt);

        // Redis에서 블랙리스트 확인 (로그아웃된 토큰인지 검사)
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String isLoggedOut = valueOperations.get(jwt);

        if (isLoggedOut != null) {
            // 로그아웃된 토큰 -> 요청 차단
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("This token has been logged out.");
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.memberService.loadUserByUsername(uuid);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}