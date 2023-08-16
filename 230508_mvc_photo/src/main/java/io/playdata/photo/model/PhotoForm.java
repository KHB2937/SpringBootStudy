package io.playdata.photo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString, hashCode, equals
@AllArgsConstructor // 모든 매개변수를 지닌 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
public class PhotoForm {
    private Long id; // 사진들을 구별하기 위한 ID
    private String title; // 사진의 제목
    private String description; // 사진의 설명
    private String imageUrl; // 사진의 주소
}