package io.playdata.diary.controller;

import io.playdata.diary.model.Diary;
import io.playdata.diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
// localhost:9080/
@RequestMapping("/") // 특정한 컨트롤러를 특정한 경로에 연결
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    /**
     * 모든 다이어리를 조회한다.
     *
     * @param model Thymeleaf 모델
     * @return 다이어리 목록 페이지
     */
    @GetMapping
    public String getAllDiaries(Model model) {
        List<Diary> diaries = diaryService.getAllDiaries();
        model.addAttribute("diaries", diaries);
        return "diaryList";
    }

    /**
     * 다이어리를 생성한다.
     *
     * @param diary 생성할 다이어리의 정보
     * @return 다이어리 목록 페이지
     */

//    @PostMapping
//    public String createDiary(Diary diary) {
//        diaryService.createDiary(diary);
//        return "redirect:/diaries";
//    }
    @PostMapping
    public String createDiary(
            @ModelAttribute("diary") Diary diary,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("soundFile") MultipartFile soundFile) throws IOException {
//        diaryService.createDiary(diary);
        diaryService.createDiary(diary, imageFile, soundFile);
        return "redirect:/";
    }

    /**
     * 다이어리를 삭제한다.
     *
     * @param id 삭제할 다이어리의 ID
     * @return 다이어리 목록 페이지
     */
    @GetMapping("/{id}/delete")
    public String deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
        return "redirect:/";
    }

    /**
     * 다이어리를 조회한다.
     *
     * @param id    조회할 다이어리의 ID
     * @param model Thymeleaf 모델
     * @return 다이어리 상세보기 페이지
     */
    @GetMapping("/{id}")
    public String getDiary(@PathVariable Long id, Model model) {
        Diary diary = diaryService.getDiary(id);
        if (diary == null) {
            return "redirect:/";
        }
        model.addAttribute("diary", diary);
        return "diaryDetail";
    }

    /**
     * 다이어리를 수정한다.
     *
     * @param id       수정할 다이어리의 ID
     * @param newDiary 변경할 다이어리의 정보
     * @return 다이어리 목록 페이지
     */
//    @PostMapping("/{id}")
//    public String updateDiary(@PathVariable Long id, Diary newDiary) {
//        Diary diary = diaryService.getDiary(id);
//        if (diary != null) {
//            diary.setTitle(newDiary.getTitle());
//            diary.setContent(newDiary.getContent());
//            diary.setImage(newDiary.getImage());
//            diary.setSound(newDiary.getSound());
//            diaryService.updateDiary(id, diary);
//        }
//        return "redirect:/diaries";
//    }

    @PostMapping("/{id}")
    public String updateDiary(@PathVariable Long id,
                              @ModelAttribute("diary") Diary newDiary,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @RequestParam("soundFile") MultipartFile soundFile) throws IOException {
        Diary diary = diaryService.getDiary(id);
        if (diary != null) {
            diary.setTitle(newDiary.getTitle());
            diary.setContent(newDiary.getContent());
//            diary.setImage(newDiary.getImage());
//            diary.setSound(newDiary.getSound());
//            diary.setCreateAt(newDiary.getCreateAt());
            diaryService.updateDiary(id, diary, imageFile, soundFile);
        }
        return "redirect:/";
    }

//    @Value("${upload.path}")
//    private String uploadPath;

    @GetMapping("/upload/{filename}") // {@PathVariable}
    public ResponseEntity upload(@PathVariable String filename) throws IOException {
//        Path filePath = Path.of(uploadPath + "/" + filename); // 경로를 잡아준다음에
        // 경로에서 바이트 그대로 읽어올 것임
//        byte[] byteArray = Files.readAllBytes(filePath);
        byte[] byteArray = diaryService.loadFile(filename);
        return new ResponseEntity<>(byteArray, HttpStatus.OK);
    }
}