package io.playdata.tshirts.repository;

import io.playdata.tshirts.model.TShirt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TShirtRepository extends JpaRepository<TShirt, Long> {
}