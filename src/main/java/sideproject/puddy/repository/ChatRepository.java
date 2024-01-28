package sideproject.puddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Chat;
import sideproject.puddy.model.Person;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByFirstPerson(Person firstPerson);
    List<Chat> findAllBySecondPerson(Person secondPerson);
boolean existsByFirstPersonAndSecondPerson(Person firstPerson, Person secondPerson);

}
