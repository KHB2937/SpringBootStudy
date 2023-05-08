package io.playdata.photo.controller;

import io.playdata.photo.model.PhotoForm;
import io.playdata.photo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // @RestController
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    // 모든 사진 정보를 조회하는 메소드
    @GetMapping("/")
    public String index(Model model) { // View(웹페이지)로 전달할 데이터 묶음
        model.addAttribute("photos", photoService.getAllPhotos()); // Forward로 전달, Redirect면 전달 X
        return "index"; // (src/main/resources/templates/)...(.html)
    }

    // 사진 추가 폼이 있는 페이지를 불러오는 메소드
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("photoForm", new PhotoForm());
        return "add"; // 어느 View로 연결해줄건지 String으로 전달
    }

    // 사진을 추가하는 메소드
    @PostMapping("/add")
    public ModelAndView add(@ModelAttribute("photoForm") @Validated PhotoForm photoForm, BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        if(bindingResult.hasErrors()) { // @Validated -> 검증
            // 검증결과에 오류가 있는 경우
            return new ModelAndView("add"); // 다시 추가페이지로 이동
            // 우리가 보기에는 새로고침인데, 실제로는 실패했으므로 갱신되어서 원래 페이지로 보내버림.
        }
        photoService.addPhoto(photoForm); // 문제가 없다면...
        mav.setViewName("redirect:/"); // 메인페이지로 redirect하겠다 -> 주소가 바뀌면서 원래 홈페이지로 가겠다
        // 얘는 안 뜸
//        mav.addObject("message", "사진이 정상 추가되었습니다"); // redirect - 안보이는게 정상
        redirectAttributes.addFlashAttribute("message", "사진이 정상 추가되었습니다");
        return mav;
    }

    // 사진 수정하는 폼 페이지를 불러오는 메소드
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("photoForm", photoService.getPhotoById(id));
        return "edit";
    }

    // 사진을 수정하는 메소드
    @PostMapping("/edit") // PhotoForm에 ID가 포함되어 있기 때문에 id를 요청할 필요가 없음
    public ModelAndView edit(@ModelAttribute("photoForm") @Validated PhotoForm photoForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        if(bindingResult.hasErrors()) {
            mav.setViewName("edit");
            return mav; // 부적절한 데이터가 전달 되서 에러가 발생 -> edit 돌아가는 것
        }
        photoService.updatePhoto(photoForm);
        mav.setViewName("redirect:/");
        // 뜸
        redirectAttributes.addFlashAttribute("message", "사진 수정이 성공했습니다");
        // 전달이 되어야 함.
        return mav;
    }

    // 사진을 삭제하는 메소드
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        photoService.deletePhoto(id);
        mav.setViewName("redirect:/"); // 메인 페이지로 삭제뒤 리다이렉트
        mav.addObject("message", "사진이 삭제되었습니다"); // 안 뜸
        return mav;
    }
}
