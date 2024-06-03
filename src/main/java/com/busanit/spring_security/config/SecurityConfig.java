package com.busanit.spring_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity // 메소드 보안 활성화
@EnableWebSecurity    // 웹 보안 활성화 선언
@Configuration        // 스프링 설정 클래스 선언
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean // 스피링 빈으로 등록 -> 사용자 인증을 처리
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) {
        try {
            return auth.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 평문 비밀번호 암호화하여 DB 에 저장(BCrypt 방법 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean   // 스프링 컨테이너가 관리하는 Bean 객체임을 선언
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // CSRF: 웹 보안 공격, 사용자가 요청할 때 인지 못한 상태에서 원하지 않는 액션을 수행하게 하는 공격
                // CSRF 보호 비활성화 (REST API 요청으로 비활성화) <- CSRF 보호가 되어있는 웹에 접근하기 위해서는 CSRF 토큰이 필요하고 검증
                .authorizeRequests(
                        auth -> auth.requestMatchers("/test", "/register", "/auth").permitAll()   // test라는 요청에 대해서는 모두 허용
                                    .anyRequest().authenticated()   // 모든 요청에 대해서 인증을 요구
                )
                .formLogin(
                        form -> form .loginPage("/login")    // 로그인 페이지의 경로
                                    .permitAll()            // 로그인 페이지는 모두에게 허용
                )
                .logout(
                        logout -> logout.permitAll()
                );
        // 로그아웃도 모두에게 허용
        return http.build();
    }
}
