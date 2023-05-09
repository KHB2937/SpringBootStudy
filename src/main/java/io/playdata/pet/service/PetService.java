package io.playdata.pet.service;

import io.playdata.pet.model.Pet;
import io.playdata.pet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }


    public void savePet(Pet pet, MultipartFile imageFile, MultipartFile voiceFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new IllegalArgumentException("이미지 파일은 JPEG 또는 PNG 형식이어야 합니다.");
            }
            String fileName = System.currentTimeMillis() + "-" + imageFile.getOriginalFilename();
            File file = new File(uploadPath + "/" + fileName);
            imageFile.transferTo(file);
            pet.setImage(fileName);
        }

        if (!voiceFile.isEmpty()) {
            String contentType = voiceFile.getContentType();
            if (!contentType.equals("audio/mpeg")) {
                throw new IllegalArgumentException("음성 파일은 MP3 형식이어야 합니다.");
            }
            String fileName = System.currentTimeMillis() + "-" + voiceFile.getOriginalFilename();
            File file = new File(uploadPath + "/" + fileName);
            voiceFile.transferTo(file);
            pet.setVoice(fileName);
        }

        petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Pet 정보를 찾을 수 없습니다."));
    }

    public void updatePet(Pet updatedPet, MultipartFile imageFile, MultipartFile voiceFile) throws IOException {
        Pet pet = getPetById(updatedPet.getId());
        pet.setName(updatedPet.getName());
        pet.setRace(updatedPet.getRace());

        if (!imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new IllegalArgumentException("이미지 파일은 JPEG 또는 PNG 형식이어야 합니다.");
            }
            String fileName = System.currentTimeMillis() + "-" + imageFile.getOriginalFilename();
            File file = new File(uploadPath + "/" + fileName);
            imageFile.transferTo(file);
            pet.setImage(fileName);
        }

        if (!voiceFile.isEmpty()) {
            String contentType = voiceFile.getContentType();
            if (!contentType.equals("audio/mpeg")) {
                throw new IllegalArgumentException("음성 파일은 MP3 형식이어야 합니다.");
            }
            String fileName = System.currentTimeMillis() + "-" + voiceFile.getOriginalFilename();
            File file = new File(uploadPath + "/" + fileName);
            voiceFile.transferTo(file);
            pet.setVoice(fileName);
        }

        petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}

