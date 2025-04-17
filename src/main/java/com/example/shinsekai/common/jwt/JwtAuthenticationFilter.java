package com.example.shinsekai.common.jwt;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.redis.RedisProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final RedisProvider redisProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String uuid;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        try {
            uuid = jwtTokenProvider.extractAllClaims(token).getSubject();
        } catch (ExpiredJwtException e) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
        }

        String storedAccessToken = redisProvider.getToken(TokenType.ACCESS, uuid);

        log.info("token {}", token);
        log.info("storedAccessToken {}", storedAccessToken);


        if (!token.equals(storedAccessToken)) {
            setErrorResponse(response, BaseResponseStatus.DUPLICATED_LOGIN);
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(uuid);
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

    private void setErrorResponse(HttpServletResponse response, BaseResponseStatus status) throws IOException {
        response.setStatus(status.getCode()); // 예: 401
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BaseResponseEntity<?> errorResponse = new BaseResponseEntity<>(status);
        String json = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(json);
    }
}