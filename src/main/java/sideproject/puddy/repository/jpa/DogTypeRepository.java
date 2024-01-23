package sideproject.puddy.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.DogType;

import java.util.Optional;

@Repository
public interface DogTypeRepository extends JpaRepository<DogType, Long> {
    Optional<DogType> findByContent(String content);
}
