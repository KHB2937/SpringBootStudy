package io.playdata.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping // ('/')
public class AccountController {
    // localhost:8080 -> 로그인 폼
    // 버튼 하나 누르면 -> 회원가입 페이지

    @GetMapping
    public String home() {
        return "index";
    }
}
