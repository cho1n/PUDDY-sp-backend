package sideproject.puddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findAllByPerson(Person person);
    Optional<Dog> findByPersonAndMain(Person person, boolean main);
    Optional<Dog> findByPersonAndId(Person person, Long id);
    boolean existsByPerson(Person person);
    boolean existsByPersonAndMain(Person person, boolean main);
}
