package sideproject.puddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Match;


import java.util.Optional;
@Repository
public interface MatchRepository extends JpaRepository<Match, Long>{
}
