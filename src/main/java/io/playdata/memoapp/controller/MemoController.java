package io.playdata.memoapp.controller;

import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.service.MemoService;
import io.playdata.memoapp.service.MemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller // 스프링에 Controller임을 등록
@RequestMapping("/memo")
public class MemoController {
    @Autowired // 의존성 주입
    private MemoService memoService;

    @PostMapping
    public String memo(@RequestParam String text,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        long userId = (long) session.getAttribute("user");
        memoService.createMemo(text, userId);
        redirectAttributes.addFlashAttribute("msg",
                "정상적으로 메모가 작성되었습니다");
        return "redirect:/"; // 인덱스 경로
    }
}
