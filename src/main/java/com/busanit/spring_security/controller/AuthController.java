package com.busanit.spring_security.controller;

import com.busanit.spring_security.jwt.JwtUtil;
import com.busanit.spring_security.model.User;
import com.busanit.spring_security.repository.UserRepository;
import com.busanit.spring_security.service.CustomUserDetailsService;
import com.busanit.spring_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor    // 필수 생성자 => 의존성 주입
public class AuthController {

    // 스프링 컴포넌트 의존성 주입
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/auth")
    public Map<String, String> authToken(@RequestBody User user) throws Exception {
        try {
            // 인증 관리자로 인증
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {   // 인증 실패시
            throw new Exception("wrong username or password", e);
        }

        // UserDetails 가져오기
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        // 토큰 생성하기 (UserDetails 정보 기반)
        String jwt = jwtUtil.generateToken(userDetails);

        // 토큰 정보를 담은 Map 을 응답으로 반환
        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwt);

        return response;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
