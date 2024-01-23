package sideproject.puddy.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
