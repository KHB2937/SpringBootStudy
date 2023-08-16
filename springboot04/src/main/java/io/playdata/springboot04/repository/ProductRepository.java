package io.playdata.springboot04.repository;

import io.playdata.springboot04.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<엔터티(model),ID의 타입>
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 생성 규칙에 따른 커스텀 메소드
    // 가격 기준으로 내림차순에서 정렬한 결과를 보여주는 메소드
    List<Product> findAllByOrderByPriceDesc();
    // https://sharegpt.com/c/afE2fuD

    // 쿼리를 직접 넣어서 Native Query 작동
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameContaining(@Param("name") String name);
    // https://sharegpt.com/c/xtWTC0q
}