package io.playdata.login.controller;

import io.playdata.login.model.Account;
import io.playdata.login.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping // ('/')
public class AccountController {
    // localhost:8080 -> 로그인 폼
    // 버튼 하나 누르면 -> 회원가입 페이지

    @GetMapping
    public String home() {
        return "index";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("account", new Account());
        return "join";
    }

    @Autowired
    private AccountService accountService;

    @PostMapping("/join")
    public String join(Model model,
                       @ModelAttribute("account") Account account,
                       RedirectAttributes redirectAttributes) {
        accountService.join(account);
        redirectAttributes.addFlashAttribute("msg", "정상 가입 완료");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(HttpSession session,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes,
                        @RequestParam("loginID") String loginID,
                        @RequestParam("password") String password) {
        Account account = accountService.login(loginID, password);
        if (account == null) {
            redirectAttributes.addFlashAttribute("msg", "존재하지 않는 회원입니다");
            return "redirect:/";
        }
        return "main";
    }
}
