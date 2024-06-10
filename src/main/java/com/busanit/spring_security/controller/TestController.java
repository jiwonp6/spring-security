package com.busanit.spring_security.controller;

import com.busanit.spring_security.model.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<Test> test() {
        Test test = new Test("테스트");
        return ResponseEntity.ok(test);
    }

    @GetMapping("/protect")
    public ResponseEntity<Test> protect() {
        Test test = new Test("보안되어있는 API 요청");
        return ResponseEntity.ok(test);
    }

    @GetMapping("/login")
    public String login() {
        return "로그인 페이지입니다.";
    }
}
