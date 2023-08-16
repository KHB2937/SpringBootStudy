package io.playdata.security.controller;

import io.playdata.security.exception.UniqueUsernameException;
import io.playdata.security.exception.UsernameLengthException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 보조용 컨트롤러
public class ExceptionHandlerController {

    @ExceptionHandler(UsernameLengthException.class)
    public String handleException(UsernameLengthException ex) {
        return "redirect:/join";
    }

    @ExceptionHandler(UniqueUsernameException.class)
    public String handleException(UniqueUsernameException ex) {
        return "redirect:/login";
    }

    @ExceptionHandler(Exception.class) // Exception으로 발생되는 것들을 처리해주겠다 (type)
    public String handleException(Exception ex, Model model) {
        // ex.getMessage() -> new Exception(message)
        // throw new Exception("중복된 Username 입니다");
        model.addAttribute("msg", ex.getMessage());
        return "error";
    }
}
