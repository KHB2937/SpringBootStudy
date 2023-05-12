package io.playdata.security.core.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandlerController { // 서버 처리 중에서 발생한 Exception 들을 처리해주는 Controller

    // DB에서 나오는 에러, 서비스나 데이터 에러...
//    @ExceptionHandler(AccessDeniedException.class) // 스프링 Security에서 권한 부족으로 들어갈 수 없을 때
//    public String handleAccessDeniedException(AccessDeniedException ex, Model model) {
//        model.addAttribute("msg", "권한이 부족합니다!");
//        return "error";
//    }

    @ExceptionHandler(Exception.class) // 에러의 타입을 넣어주는 개념.
    public String handleException(Exception ex, Model model) {
        model.addAttribute("msg", ex.getMessage());
        return "error";
    }
}
