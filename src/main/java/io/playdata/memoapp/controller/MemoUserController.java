package io.playdata.memoapp.controller;

import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.service.MemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
