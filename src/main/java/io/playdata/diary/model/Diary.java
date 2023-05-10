package io.playdata.diary.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

// model, VO, DTO
// 2개의 어노테이션
@Entity // JPA가 DB와 소통할 때 쓸 데이터로 인식
@Data // Lombok - Getter, Setter, ...
public class Diary {
    // id, title, content, image, sound, createAt
    @Id // DB와 소통할 때 단독으로 쓰일 기준값
    @GeneratedValue // 이걸 추가로 생성할 때마다 1씩 증가
    private Long id;
    private String title;
    private String content;
    private String image; // 파일 -> 별도의 스토리지(창고)에 업로드 되어야함 -> 업로드된 경로 또는 파일 이름
    private String sound; // 파일 -> ...
    private LocalDateTime createAt; // 생성 당시의 시간을 기록

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}