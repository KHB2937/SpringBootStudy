package io.playdata.memoapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity // JPA
@Data // Lombok
@Table(name="MEMO_USER")
public class MemoUserDTO {
    // ID : User들간의 구분을 위한 식별자 (유저별로 1씩 존재)
    @Id // JPA
    // GenerationType.IDENTITY -> 1부터 시작 지정.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 증가시켜주겠다 (JPA)
    private Long id;
    // 로그인용 인증정보
    private String loginId;
    private String password;
    // 개별 정보
    private String name;
    private String image; // 프로필 이미지 (파일을 업로드 -> 파일명)
}
