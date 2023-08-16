package io.playdata.diary.repository;

import io.playdata.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    // 기초적인 CRUD 메소드를 기본으로 가지고 있음 (find, save, delete...)
    // 생성 메소드
    // 쿼리
}