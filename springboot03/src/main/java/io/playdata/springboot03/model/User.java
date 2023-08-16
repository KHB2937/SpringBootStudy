package io.playdata.springboot03.model;

import lombok.*; // 패키지명.* -> 그 안에 있는 모든 요소들을 불러오기

import javax.persistence.*;

@Entity // JPA에서 사용하는 대상
@Table(name="account") // SQL에 들어갈 테이블 -> name=account? -> user 이름이 예약어 (버그)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;
}

/*
이 코드는 Spring Boot 프로젝트에서 사용될 User 엔티티 클래스입니다.
@Entity: JPA에서 사용되는 엔티티 클래스임을 알리는 어노테이션입니다. 이를 사용하면, User 클래스는 데이터베이스의 테이블과 매핑됩니다.
@Table(name = "user"): 매핑할 데이터베이스 테이블의 이름을 정의하는 어노테이션입니다. 이를 사용하면, User 클래스는 데이터베이스의 user 테이블과 매핑됩니다.
@Getter, @Setter: Lombok 어노테이션으로, 해당 클래스에서 필드에 대한 getter, setter 메소드를 자동 생성해 줍니다. 이를 사용하면, User 클래스에서 id, name, email 필드에 대한 getter, setter 메소드를 간편하게 작성할 수 있습니다.
@NoArgsConstructor, @AllArgsConstructor: Lombok 어노테이션으로, 파라미터가 없는 기본 생성자와 모든 필드를 파라미터로 받는 생성자를 자동으로 생성해 줍니다. 이를 사용하면, User 객체를 생성하거나, 필드 값을 변경하는 작업을 간편하게 수행할 수 있습니다.
@ToString: Lombok 어노테이션으로, 해당 클래스에서 toString() 메소드를 자동 생성해 줍니다. 이를 사용하면, User 객체의 내용을 문자열로 표현하는 작업을 간편하게 수행할 수 있습니다.
@Id: JPA에서 사용되는 엔티티 클래스의 식별자를 지정하는 어노테이션입니다. 이를 사용하면, User 클래스에서 id 필드가 엔티티의 식별자임을 명시할 수 있습니다.
@GeneratedValue(strategy = GenerationType.IDENTITY): 엔티티의 식별자 값을 자동으로 생성할 때 사용하는 어노테이션입니다. 이를 사용하면, User 클래스에서 id 필드 값이 자동으로 생성됩니다.
@Column(nullable = false, length = 50): 데이터베이스 테이블의 컬럼에 대한 정보를 정의하는 어노테이션입니다. 이를 사용하면, User 클래스에서 name과 email 필드에 대한 컬럼 정보를 정의할 수 있습니다. nullable = false로 지정하면, name과 email 필드 값이 null이 될 수 없음을 명시합니다. length = 50으로 지정하면, name과 email 필드 값이 최대 50자까지 저장될 수 있도록 합니다.
위와 같이 User 엔티티 클래스를 정의하면, JPA를 사용하여 데이터베이스와 연동하는 작업을 보다 쉽게 할 수 있습니다.
 */