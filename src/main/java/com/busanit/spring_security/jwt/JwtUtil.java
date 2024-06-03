package com.busanit.spring_security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component  // 스프링 컴포넌트로 등록
public class JwtUtil {
    // 암호키(SHA 256 암호화 알고리즘으로 생성한 키)
    private final String secret = "05bd8c91abeea3a8d28e6bdde0f862c26bcd700a8226f451468500c2bd2cd8d6";
    // 토큰 만료기간
    private final long expiration = 86400000; 

    // 비밀키 생성
    private SecretKey getSigningkey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 특정 클레임 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // JWT 토큰을 파싱해서 클레임 추출
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getSigningkey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        // 추출한 클레임 <- 함수형 인터페이스로 처리
        return claimsResolver.apply(claims);
        
    }
    
    // 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰 만료 여부 확인
    private Boolean isTokenExpired(String token) {
        // 토큰의 만료 날짜가 현재 날짜와 비교하여 이전인지에 따라 t/f
        return extractExpiration(token).before(new Date());
    }

    //
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                    .setClaims(claims)      // 클레임 설정
                    .setSubject(subject)    // 사용자 이름 설정
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 토큰 발급시간
                    .signWith(getSigningkey(), SignatureAlgorithm.HS256)              // 토큰 만료 시간
                    .compact();             // 문자열로 리턴
    }
    
    // 토큰 생성
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();   // 빈 클레임 맵 컬렉션
        // 클래임 맵과 스프링 시큐리티에서 가져온 사용자 이름 정보로 토큰 생성
        return createToken(claims, userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        // 토큰 사용자 이름 = 스프링 유저 사용자 이름
        boolean isEqualUsername = username.equals(userDetails.getUsername());

        // 토큰 만료기간 확인
        boolean isNotExpired = !isTokenExpired(token);

        return isEqualUsername && isNotExpired;
    }

}
