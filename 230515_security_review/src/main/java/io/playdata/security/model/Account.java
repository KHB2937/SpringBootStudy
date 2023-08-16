package io.playdata.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // JPA
@Data // Lombok
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // JPA에서 사용해줄 (데이터 간의 구분이 가능한) 고유한 값
    private String username; // id로 쓰일 값
    private String password; // 암호
    private String role; // 권한
}
