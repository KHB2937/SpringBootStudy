package io.playdata.tshirts.controller;

import io.playdata.tshirts.model.TShirt;
import io.playdata.tshirts.service.TShirtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TShirtController {
    @Autowired
    private TShirtService tShirtService;

    @GetMapping("/") // @GetMapping -> 어떠한 주소로 Get이라는 요청이 들어왔을 때 이 메소드를 사용해주겠다
    // Get or Post -> 일반적인 홈페이지에서는 링크로 접속하거나(Get) Form으로 요청 (Get/Post)
    public String viewHomePage(Model model) { // 메소드 -> Input / Output
        // Input -> Model (패러미터, 인자) -> View로 전달할 데이터나 설정값 (담아줄 수 있는 그릇)
        // Output -> View (HTML) -> HTML / 템플릿엔진 (Model을 통해서 전달 받은 데이터를 표현)
        List<TShirt> tShirts = tShirtService.getAllTShirts(); // tShirts 데이터를 DB로부터 Service를 통해 끌어왔고,
//        model.addAttribute("message", "티셔츠 많이 팔고 싶다");
        model.addAttribute("message", "티셔츠 50% 할인 중!");
        model.addAttribute("tShirts", tShirts); // 그 데이터를 Model에 담았음.
        return "index"; // 어떠한 html 파일에 연동할 것이냐? (resources/templates/{index}.html)
    }

    @GetMapping("/showNewTShirtForm")
    public String showNewTShirtForm(Model model) {
        TShirt tShirt = new TShirt();
        model.addAttribute("message", "당신의 티셔츠를 만들어보세요!");
        model.addAttribute("tShirt", tShirt);
        return "new_tshirt";
    }

    @PostMapping("/saveTShirt")
    public String saveTShirt(@ModelAttribute("tShirt") TShirt tShirt) {
        tShirtService.addTShirt(tShirt);
        return "redirect:/";
    }

    @GetMapping("/showUpdateTShirtForm/{id}")
    public String showUpdateTShirtForm(@PathVariable(value = "id") Long id, Model model) {
        TShirt tShirt = tShirtService.getTShirtById(id);
        model.addAttribute("tShirt", tShirt);
        return "update_tshirt";
    }

    @PostMapping("/updateTShirt")
    public String updateTShirt(@ModelAttribute("tShirt") TShirt tShirt) {
        tShirtService.updateTShirt(tShirt);
        return "redirect:/";
    }

    @GetMapping("/deleteTShirt/{id}")
    public String deleteTShirt(@PathVariable(value = "id") Long id) {
        tShirtService.deleteTShirtById(id);
        return "redirect:/";
    }
}