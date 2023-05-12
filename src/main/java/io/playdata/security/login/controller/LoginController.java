package io.playdata.security.login.controller;

import io.playdata.security.login.model.AccountDTO;
import io.playdata.security.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // 스프링에게 등록
public class LoginController {
    // 비즈니스 로직 (실제로 데이터를 조작하거나 변형) -> Service
    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    private String postRegister(
        AccountDTO user,
        RedirectAttributes redirectAttributes
    ) {
        // throws -> 에러처리 상위 메소드로 위임
        try {
            loginService.register(user);
        } catch (Exception e) {
//            throw new Exception("...") -> msg
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("msg", "정상적으로 등록됐습니다");
        return "redirect:/login"; // ~/login 페이지로 리다이렉트
    }

    @GetMapping("register")
    public String getRegister(Model model) {
        // TODO : 로그인 여부를 감지해서 들어오지 못하게
        model.addAttribute("user", new AccountDTO());
        return "register"; // templates/register.html
    }
}
