package com.example.bookhive;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LibraryController {
    @GetMapping("/") // GET 방식으로 "path"를 호출했을 때, 어떠한 응답을 줄지
    public String home(Model model) {
        // model <= 페이지로 전달할 데이터들의 묶음
        model.addAttribute("pageTitle", "Bookhive Library");
        return "home"; // templates -> home.html
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About Playdata Library");
        return "about";
    }
}