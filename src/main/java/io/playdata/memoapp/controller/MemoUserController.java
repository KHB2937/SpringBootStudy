package io.playdata.memoapp.controller;

import io.playdata.memoapp.service.MemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 스프링에 Controller임을 등록
@RequestMapping // @RequestMapping("/") -> 기본 index 경로, 루트 ('/') 경로에 이 컨트롤러를 연결
public class MemoUserController {
    // 비즈니스 로직을 사용해주기 위해서 Service
    @Autowired // 의존성 주입
    private MemoUserService memoUserService;
}
