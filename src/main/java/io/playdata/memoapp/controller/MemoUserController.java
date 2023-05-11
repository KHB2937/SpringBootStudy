package io.playdata.memoapp.controller;

import io.playdata.memoapp.service.MemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 스프링에 Controller임을 등록
@RequestMapping // @RequestMapping("/") -> 기본 index 경로, 루트 ('/') 경로에 이 컨트롤러를 연결
public class MemoUserController {
    // 비즈니스 로직을 사용해주기 위해서 Service
    @Autowired // 의존성 주입
    private MemoUserService memoUserService;

    @GetMapping("/") // http://localhost:8080/
    public String index() { // String -> 연결할 view의 이름
        // TODO : 로그인이 되었을 때
        // 로그인이 안 되었을 때
        return "login"; // prefix -> (templates/)login(.html) <- suffix
    }

}
