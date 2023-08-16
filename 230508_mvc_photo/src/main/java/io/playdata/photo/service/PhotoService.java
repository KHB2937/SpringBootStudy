package io.playdata.photo.service;


import io.playdata.photo.model.Photo;
import io.playdata.photo.model.PhotoForm;
import io.playdata.photo.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    // Photo To Form
    private PhotoForm mapToForm(Photo photo) {
        PhotoForm photoForm = new PhotoForm();
        photoForm.setId(photo.getId());
        photoForm.setTitle(photo.getTitle());
        photoForm.setDescription(photo.getDescription());
        photoForm.setImageUrl(photo.getImageUrl());
        return photoForm;
    }

    // PhotoForm (View -> Form) => Photo
    public void addPhoto(PhotoForm photoForm) {
        Photo photo = new Photo();
        // ID는 설정 X -> ID는 알아서 생성
        photo.setTitle(photoForm.getTitle());
        photo.setDescription(photoForm.getDescription());
        photo.setImageUrl(photoForm.getImageUrl());
        photoRepository.save(photo); // JPA 통해서 데이터 저장
    }

    // 모든 사진 정보를 조회하는 메소드
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    // id로 사진 정보를 조회하는 메소드
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Photo Id: " + id)
                // 없는 ID일 경우에 에러 발생
        );
    }

    // PhotoForm으로 사진 정보를 수정하는 메소드
    // 사진 수정 페이지 -> DB에 저장되어있던 데이터를 Form 형태로 불러들여서 수정
    public void updatePhoto(PhotoForm photoForm) {
        Photo photo = new Photo();
        photo.setId(photoForm.getId()); // 수정 -> ID O
        photo.setTitle(photoForm.getTitle());
        photo.setDescription(photoForm.getDescription());
        photo.setImageUrl(photoForm.getImageUrl());
        photoRepository.save(photo);
    }

    // ID로 사진 정보를 삭제하는 메소드
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

}
