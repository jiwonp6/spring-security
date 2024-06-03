package com.busanit.spring_security.controller;

import com.busanit.spring_security.model.User;
import com.busanit.spring_security.service.CustomUserDetailsService;
import com.busanit.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired // 스프링 시큐리티 유저 서비스
    CustomUserDetailsService customUserService;
    @Autowired // 일반 유저 서비스 DI
    UserService userService;

    @Autowired // 인증 관리자 DI
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public String auth(@RequestBody User user) throws Exception {
        try {
            // 스프링 시큐리티 인증관리자로 사용자로부터 받은 정보로 인증 토큰을 생성하여 인증
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            authenticationManager.authenticate(authToken);
    
            // 인증된 사용자 정보를 가져옴
            customUserService.loadUserByUsername(user.getUsername());
    
            return String.format("%s님, 성공적으로 인증되었습니다.", user.getUsername());
            
        } catch (BadCredentialsException e) {
            // 인증 실패되는 경우
            throw new Exception("인증에 실패하였습니다.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("회원가입을 축하드립니다.");
    }

}
