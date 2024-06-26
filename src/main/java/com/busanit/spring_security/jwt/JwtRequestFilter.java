package com.busanit.spring_security.jwt;

import com.busanit.spring_security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 스프링 웹 필터 클래스를 상속받은 Jwt 요청 필터
@Component      // 컴포넌트(스프링) 등록
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired      // DI
    private CustomUserDetailsService userDetailsService;

    @Autowired      // DI
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // HTTP 요청 헤더에서 Authorization 정보를 가져옴
        String authorization = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        // 인증 헤더 정보가 존재하고 "Bearer "로 시작하면
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);  // jwt 토큰을 추출
            username = jwtUtil.extractUsername(jwt);     // 사용자이름 추출
        }

        // 토큰에 사용자 이름은 존재하는데, SecurityContextHolder 에 인증이 되지 않은 경우
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 사용자 정보를 불러옴
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 사용자 정보를 불러오니, 토큰이 유효한 경우
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // 스프링 시큐리티 인증 토큰 생성
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // 인증 요청 세부정보를 설정
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                // SecurityContext 에 인증 정보를 설정
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 다음 필터로 요청, 응답 정보 전달
        filterChain.doFilter(request, response);
    }
}
