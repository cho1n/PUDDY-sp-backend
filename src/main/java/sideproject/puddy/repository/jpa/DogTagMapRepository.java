package sideproject.puddy.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.DogTag;
import sideproject.puddy.model.DogTagMap;

import java.util.List;

@Repository
public interface DogTagMapRepository extends JpaRepository<DogTagMap, Long> {
    List<DogTagMap> findAllByDog(Dog dog);
}
