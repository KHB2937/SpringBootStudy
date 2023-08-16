package io.playdata.springboot03.repository;

import io.playdata.springboot03.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Repository로 '등록'
public interface UserRepository extends JpaRepository<User, Long> {
} // 알아서 메서드들이 생김

/*
위 코드에서는 JpaRepository를 상속받아서 UserRepository 인터페이스를 정의하고 있으며,
User 엔티티와 Long 타입의 id를 사용하도록 지정하고 있습니다.
또한, Repository 어노테이션을 사용하여 스프링에게 이 인터페이스가 데이터 액세스 레이어임을 알리고 있습니다.
 */