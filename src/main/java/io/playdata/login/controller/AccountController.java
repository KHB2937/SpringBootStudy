package io.playdata.login.controller;

import io.playdata.login.model.Account;
import io.playdata.login.service.AccountService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller // thymeleaf 파일을 사용해서 View
@RequestMapping // ('/') -> 루트 경로
public class AccountController {
    // localhost:8080 -> 로그인 폼
    // 버튼 하나 누르면 -> 회원가입 페이지

//    @GetMapping
//    public String home() {
//        return "index";
//    }

    @GetMapping
    public String home(
            HttpSession session // 컨트롤러에서 세션을 사용할 수 있게 하는 옵션
    ) {
        // session 자체가 지워졌거나, 존재하지 않으면 -> null
        // 생기긴했는데, login == true가 아니면, 로그인 페이지로 이동
        // session.getAttribute("login") != null -> 세션이 있긴 있는데, login 속성이 X
        if (session != null && session.getAttribute("login") != null) {
            boolean login = (boolean) session.getAttribute("login");
            if (login) {
                return "redirect:/main"; // main으로 리다이렉트
            }
        }
        // model.setAttribute("msg", "...") <= msg 우선권
        return "index"; // 로그인할 수 있는 페이지로 연결 -> forward : 주소가 그대로.
    }

    @GetMapping("/join") // <a href="/join">...</a>
    public String joinPage(Model model) {
        model.addAttribute("account", new Account());
        return "join"; // join.html (forward)
    }

    @Autowired
    private AccountService accountService; // 의존성 주입

    // <form th:object="${account}" action="/join" method="post">
    @PostMapping("/join")
    public String join(Model model,
                       @ModelAttribute("account") Account account, // th:object="${account}" input들로 name으로 연결이 되서 값이 주입
                       RedirectAttributes redirectAttributes) {
        accountService.join(account);
        redirectAttributes.addFlashAttribute("msg", "정상 가입 완료");
        // <h1 th:text="${msg}"></h1> (index.html)
        return "redirect:/";
    }

//    <form action="/login" method="post">
    @PostMapping("/login")
    public String login(HttpSession session, // 세션
                        HttpServletResponse response, // 쿠키
                        RedirectAttributes redirectAttributes, // 리다이렉트
                        @RequestParam("loginID") String loginID, // name="loginID"
                        @RequestParam("password") String password // name="password"
    ) {
        Account account = accountService.login(loginID, password);
        // 유저가 없으면 null
        // 로그인 실패시 / 페이지로 이동
        if (account == null) {
            redirectAttributes.addFlashAttribute("msg", "존재하지 않는 회원입니다");
//            <h1 th:text="${msg}"></h1>
            return "redirect:/";
        }
        // 로그인 성공 시
        session.setAttribute("login", true);
        response.addCookie(new Cookie(
                "name",  account.getName()
        ));
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(
            HttpSession session,
            @CookieValue("name") String name,
            Model model
    ) {
        if (session != null && session.getAttribute("login") != null) {
            boolean login = (boolean) session.getAttribute("login");
            if (login) {
                model.addAttribute("msg", name + "님이 로그인하셨습니다!");
                return "main";
            }
        }
        return "redirect:/"; // 로그인 없이 접근하려는 시도
    }

    @GetMapping("/logout")
    public String logout(
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session != null) {
            session.invalidate(); // 세션 만료
            redirectAttributes.addFlashAttribute(
                    "msg",
                    "정상적으로 로그아웃되었습니다");
        }
        return "redirect:/";
    }

    @GetMapping("/userlist")
    public String getUserList(Model model) {
        // 회원 목록 데이터를 조회하고 모델에 추가하는 로직을 작성
        List<Account> userList = accountService.getAllAccounts();
        model.addAttribute("userList", userList);

        return "userlist"; // 사용할 Thymeleaf 템플릿 파일의 이름
    }
    // 수정 페이지
    @GetMapping("/edit")
    public String showUpdatePage(@RequestParam("id") Long id, Model model) {
        // id에 해당하는 회원 정보 조회
        Account account = accountService.getAccountById(id);
        if (account == null) {
            // 회원 정보가 없으면 적절한 에러 처리
            return "error";  // 에러 페이지로 이동하거나 다른 방식으로 처리할 수 있음
        }

        model.addAttribute("user", account);
        return "edit";  // 수정 페이지로 이동
    }

    // 수정 작업 처리
    @PostMapping("/update")
    public String update(@ModelAttribute("user") Account account) {
        // 회원 정보 업데이트 작업 수행
        accountService.updateUser(account);
        return "redirect:/userlist";  // 수정 완료 후 회원 목록 페이지로 이동
    }

    @GetMapping("/delete")
    public String deleteAccount(@RequestParam("id") Long id) {
        // 회원 정보 삭제 작업 수행
        accountService.deleteAccountById(id);
        return "redirect:/userlist";  // 삭제 완료 후 회원 목록 페이지로 이동
    }
}
