package sideproject.puddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Post;
import sideproject.puddy.model.PostLike;


@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostAndPerson(Post post, Person person);
    PostLike findByPostAndPerson(Post post, Person person);
}
