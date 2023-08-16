package io.playdata.springboot02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {
    @RequestMapping("/") // http://ip:port "/" 는 최상위
    public String index(){
        return "index";
    }
    @RequestMapping("/name")
    public String name(Model model){
        model.addAttribute("name", "Jone");
        return "index";
    }
}
