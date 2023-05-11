package io.playdata.memoapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // JPA
@Data // Lombok
public class MemoUserDTO {
    // ID : User들간의 구분을 위한 식별자 (유저별로 1씩 존재)
    @Id // JPA
    @GeneratedValue // 1씩 증가시켜주겠다 (JPA)
    Long id;
    // 로그인용 인증정보
    String loginId;
    String password;
    // 개별 정보
    String name;
    String image; // 프로필 이미지 (파일을 업로드 -> 파일명)
}
