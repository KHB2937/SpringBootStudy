package io.playdata.rest.repository;

import io.playdata.rest.model.StoreDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreDTO, Long> {
    List<StoreDTO> findByNameContaining(String kwd);

    List<StoreDTO> findAllByOrderByPriceAsc();

    List<StoreDTO> findAllByOrderByPriceDesc();
}