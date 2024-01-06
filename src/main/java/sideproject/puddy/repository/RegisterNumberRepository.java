package sideproject.puddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.RegisterNumber;

import java.util.Optional;

@Repository
public interface RegisterNumberRepository extends JpaRepository<RegisterNumber, Long> {
    boolean existsByRegisterNum(Long registerNum);
}
