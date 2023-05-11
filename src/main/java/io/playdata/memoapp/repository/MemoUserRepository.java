package io.playdata.memoapp.repository;

import io.playdata.memoapp.model.MemoUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 스프링에 등록
public interface MemoUserRepository extends JpaRepository<MemoUserDTO, Long> { // <사용할 엔터티, 엔터티 ID타입>
    // loginId와 password를 사용해서 User를 찾는 메소드
}
