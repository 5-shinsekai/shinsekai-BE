package com.example.shinsekai.common.jwt;

import com.example.shinsekai.member.application.MemberService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("in doFilterInternal!!");

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String uuid;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("authHeader = " + authHeader);
            System.out.println("✅ doFilter before: " + SecurityContextHolder.getContext().getAuthentication());
            filterChain.doFilter(request, response);
            System.out.println("✅ doFilter after: " + SecurityContextHolder.getContext().getAuthentication());
            return;
        }

        System.out.println("Are U here??");


        jwt = authHeader.substring(7);
//        uuid = Jwts.parser().verifyWith((SecretKey) jwtTokenProvider.getSignKey())
//                .build().parseSignedClaims(jwt).getPayload().get("uuid", String.class);
        uuid = jwtTokenProvider.validateAndGetUserUuid(jwt);

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