package sideproject.puddy.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.DogTag;

import java.util.Optional;

@Repository
public interface DogTagRepository extends JpaRepository<DogTag, Long> {
    Optional<DogTag> findByContent(String content);
}
