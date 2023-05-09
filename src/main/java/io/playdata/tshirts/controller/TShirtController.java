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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/showNewTShirtForm") // forward로 던지기 때문에 이 url
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
        TShirt tShirt = tShirtService.getTShirtById(id); // id를 통해서 조회된 tshirt를
        model.addAttribute("tShirt", tShirt); // model을 통해서 전달
        return "update_tshirt"; // view의 링크로 forward.
        // model을 유지하고 전달하고, url 주소도 그대로
    }

    @PostMapping("/updateTShirt")
    public String updateTShirt(@ModelAttribute("tShirt") TShirt tShirt,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        tShirtService.updateTShirt(tShirt);
        model.addAttribute("message", "수정이 완료되었습니다!");
        // 위의 코드는 작동하지 않음 -> forward 방식이라서.
        redirectAttributes.addFlashAttribute("message", "수정이 완료되었습니다!");
        redirectAttributes.addFlashAttribute("message2", "수정이 완료되었습니다!");
        return "redirect:/"; // redirect 방식으로 index로 이동. -> 주소값을 던지려는 그 대상으로 바꾸면서 model을 유지하지 않은채로 전달
    }

    @GetMapping("/deleteTShirt/{id}")
    public String deleteTShirt(@PathVariable(value = "id") Long id,
                               RedirectAttributes redirectAttributes) {
        tShirtService.deleteTShirtById(id);
        redirectAttributes.addFlashAttribute("message2", "삭제가 완료되었습니다!");
        return "redirect:/";
    }
}