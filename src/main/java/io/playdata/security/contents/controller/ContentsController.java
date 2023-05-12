package io.playdata.security.contents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contents") // .../contents/...
public class ContentsController {

    // http://localhost:8080/contents/basic
    @GetMapping("/basic")
    public String getBasic(Model model) {
        model.addAttribute("grade", "베이직 등급");
        return "contents";
    }

    // http://localhost:8080/contents/premium
    @GetMapping("/premium")
    public String getPremium(Model model) {
        model.addAttribute("grade", "프리미엄 등급");
        return "contents";
    }
}
