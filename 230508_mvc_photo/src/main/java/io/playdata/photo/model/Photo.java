package io.playdata.photo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Lombok
@Data // getter, setter, toString, hashCode, equals
@AllArgsConstructor // 모든 매개변수를 지닌 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
// JPA
@Entity // JPA의 DTO, VO로 취급해주겠다
public class Photo {
    @Id // 값을 구분하기 위한 고윳값 (기본키, 인조키...)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성
    private Long id; // 사진들을 구별하기 위한 ID
    private String title; // 사진의 제목
    private String description; // 사진의 설명
    private String imageUrl; // 사진의 주소
}
