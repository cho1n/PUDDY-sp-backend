package sideproject.puddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Match;
import sideproject.puddy.model.Person;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>{
    boolean existsBySenderAndReceiver(Person sender, Person receiver);


    // List<Match> findByGenderAndMatched(boolean matched, boolean gender);

    @Query(value = "select p from Person p " +
            "join Match m on p.id = m.receiver.id " +
            "where p.gender = :gender " +
            "and (m.id is null or m.sender.id = :currentUserId) " +
            "and cast(ST_DISTANCE_SPHERE(POINT(p.longitude, p.latitude), POINT(:searchLongitude, :searchLatitude)) as int) <= 3000"
    )
    Page<Person> findNearPersonNotMatched(
            @Param("currentUserId")
            Long currentUserId,
            @Param("gender")
            boolean gender,
            @Param("searchLongitude") Double searchLongitude,
            @Param("searchLatitude") Double searchLatitude,
            Pageable pageable
    );


}
