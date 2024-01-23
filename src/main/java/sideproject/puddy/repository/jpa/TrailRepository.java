package sideproject.puddy.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Trail;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Long> {
}
