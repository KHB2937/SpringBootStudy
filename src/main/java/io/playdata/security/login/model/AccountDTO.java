package io.playdata.security.login.model;

import lombok.Data;

import javax.persistence.*;

@Entity // JPA
@Data // Lombok -> 유용한 메소드
@Table(name = "accounts") // 이 엔티티가 사용하는 DB 이름(JPA가 생성해줌)을 accounts로 하겠다
public class AccountDTO {
    @Id // Repository를 사용할 때 검색하는 기본 ID로 사용해줄 속성
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 생성할 때마다 1씩 늘어나면서 자동으로 부여
    private Long id;
    private String username;
    private String password;
    private String role;
}
