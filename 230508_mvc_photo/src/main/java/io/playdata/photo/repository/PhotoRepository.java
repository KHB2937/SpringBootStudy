package io.playdata.photo.repository;

import io.playdata.photo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface PhotoRepository extends CrudRepository<Photo, Long> {
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
