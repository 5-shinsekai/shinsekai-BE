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
            setErrorResponse(response, BaseResponseStatus.TOKEN_NOT_VALID);
            return;
        } catch (Exception e) {
            setErrorResponse(response, BaseResponseStatus.WRONG_JWT_TOKEN);
            return;
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

    // 필터 내에서 error throw하면 것이 불가능(@ControllerAdvice에서 처리하기 때문)
    // json을 클라이언트에 던지는 메서드를 정의하고 활용함
    private void setErrorResponse(HttpServletResponse response, BaseResponseStatus status) throws IOException {
        response.setStatus(status.getHttpStatusCode().value()); // 예: 401
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BaseResponseEntity<?> errorResponse = new BaseResponseEntity<>(status);
        String json = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}