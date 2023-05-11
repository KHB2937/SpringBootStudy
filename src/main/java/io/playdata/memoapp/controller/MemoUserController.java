package io.playdata.memoapp.controller;

import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.service.MemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller // 스프링에 Controller임을 등록
@RequestMapping // @RequestMapping("/") -> 기본 index 경로, 루트 ('/') 경로에 이 컨트롤러를 연결
public class MemoUserController {
    // 비즈니스 로직을 사용해주기 위해서 Service
    @Autowired // 의존성 주입
    private MemoUserService memoUserService;

    @GetMapping("/") // http://localhost:8080/
    public String index(
            HttpSession session,
            Model model,
            // Cookie cookie = new Cookie("name", user.getName());
            // required = false : 없어도 됨 (기본 값은 required = true)
            @CookieValue(value = "name", required = false) String name) { // String -> 연결할 view의 이름
        // 로그인이 되었을 때
        if (session != null) { // session == null
            // 1) 쿠키에 저장된 session ID를 날려버렸을 때
            // 2) 애초에 없었을 때
            // 세션 안에서는 모든 타입이 Object
            Object login = session.getAttribute("login");
            if (login != null) { // set 안해준 attribute는 null 취급
                // session은 있는데 login을 안한 거
                if ((boolean) login) { // 형변환
                    // (조건 ? true 값 : false 값)
                    // (name != null ? name : "알 수 없음")
                    model.addAttribute("msg",  (name != null ? name : "알 수 없음") + "님의 로그인을 환영합니다");
                    return "main"; // forward
                }
            }
        }
        // 로그인이 안 되었을 때
        return "login"; // prefix -> (templates/)login(.html) <- suffix
    }

    // 가입 페이지 (/join) -> GetMapping
    @GetMapping("/join")
//    public String join() {
    public String join(Model model) {
        // TODO : 로그인이 되었을 때 -> 메인으로 돌려주는.
        // 폼 입력할 때 입력을 받을 객체 전달 (user)
        model.addAttribute("user", new MemoUserDTO());
        return "join"; // join.html
    }

    // post /join
    @PostMapping("/join")
    public String joinUser(
            @ModelAttribute("user") MemoUserDTO user, // th:object="${user}"
            // TODO : imageFile
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        MemoUserDTO createdUser = memoUserService.createMemoUser(user);
        if (createdUser == null) { // ID가 중복된 것
//            return "redirect:/join";
            model.addAttribute("msg", "중복된 ID입니다");
            return "join";
        }
        // <h3 th:text="${msg}"></h3>
        redirectAttributes.addFlashAttribute("msg", "정상적으로 가입되었습니다");
        return "redirect:/"; // '/' 경로로 주소값까지 바꿔주면서 이동 시키겠다
    }

    // post /login
    @PostMapping("/login")
    public String login(
            @RequestParam("loginId") String loginId, // <input name="loginId" type="text">
            @RequestParam("password") String password, // <input name="loginId" type="text">
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session, // session을 다뤄주는 것
            HttpServletResponse response // cookie 관련
    ) {
        MemoUserDTO user = memoUserService.login(loginId, password);
        // 로그인 실패
        if (user == null) {
            redirectAttributes.addFlashAttribute("msg", "인증 정보가 올바르지 않습니다");
            return "redirect:/";
        }
        // 로그인 성공
//        model.addAttribute("msg", user.getName() + "님의 로그인을 환영합니다");
        // -> redriect라서 작동 안하는 코드
        session.setAttribute("login", true); // login 여부를 true 바꾸고, 그 세션을 서버 저장
        Cookie cookie = new Cookie("name", user.getName());
        cookie.setMaxAge(60 * 60); // 1시간 (1초 단위)
        response.addCookie(cookie);
        // db 같은 저장 (Spring Session) -> cookie : Session ID
//        return "main";
        return "redirect:/"; // index
    }

    // get /logout
    @GetMapping("/logout")
    public String logout(HttpSession session,
                         RedirectAttributes redirectAttributes,
                         HttpServletResponse response) {
        if (session != null) { // 세션이 존재하면
            session.invalidate(); // 세션이 만료 및 삭제
            Cookie cookie = new Cookie("name", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            redirectAttributes.addFlashAttribute("msg", "정상적으로 로그아웃 되었습니다");
        }
        return "redirect:/";
    }
}
