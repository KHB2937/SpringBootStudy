package io.playdata.memoapp.repository;

import io.playdata.memoapp.model.MemoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 스프링에 등록
public interface MemoRepository extends JpaRepository<MemoDTO, Long> { // <사용할 엔터티, 엔터티 ID타입>
    List<MemoDTO> findAllByUserIdOrderByIdDesc(long userId);
}
