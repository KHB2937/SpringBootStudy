package io.playdata.security.core.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController { // 서버 처리 중에서 발생한 Exception 들을 처리해주는 Controller

    @ExceptionHandler(Exception.class) // 에러의 타입을 넣어주는 개념.
    public String handleException(Exception ex) {
        return "error";
    }
}
