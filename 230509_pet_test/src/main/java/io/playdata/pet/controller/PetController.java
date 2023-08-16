package io.playdata.pet.controller;

import io.playdata.pet.model.Pet;
import io.playdata.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/")
    public String listPets(Model model) {
        model.addAttribute("pets", petService.getAllPets());
        return "index";
    }

    @GetMapping("/add")
    public String addPet(Model model) {
        model.addAttribute("pet", new Pet());
        return "add";
    }

    @PostMapping("/")
    public String savePet(@ModelAttribute("pet") Pet pet,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          @RequestParam("voiceFile") MultipartFile voiceFile,
                          Model model) throws IOException {
        try {
            petService.savePet(pet, imageFile, voiceFile);
            model.addAttribute("successMessage", "Pet 정보가 등록되었습니다.");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pets", petService.getAllPets());
            return "index";
        }
    }

    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/upload/{filename}")
    public ResponseEntity<?> getUploadedFile (@PathVariable String filename) throws IOException {
        Path filePath = Path.of(uploadPath + "/" + filename);
        byte[] byteArray = Files.readAllBytes(filePath);
        return new ResponseEntity<>(byteArray, HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public String editPet(@PathVariable Long id, Model model) {
        Pet pet = petService.getPetById(id);
        model.addAttribute("pet", pet);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePet(@ModelAttribute("pet") Pet pet,
                            @RequestParam("imageFile") MultipartFile imageFile,
                            @RequestParam("voiceFile") MultipartFile voiceFile,
                            Model model) throws IOException {
        try {
            petService.updatePet(pet, imageFile, voiceFile);
            model.addAttribute("successMessage", "Pet 정보가 수정되었습니다.");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pets", petService.getAllPets());
            return "index";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id, Model model) {
        petService.deletePet(id);
        model.addAttribute("successMessage", "Pet 정보가 삭제되었습니다.");
        return "redirect:/";
    }
}
