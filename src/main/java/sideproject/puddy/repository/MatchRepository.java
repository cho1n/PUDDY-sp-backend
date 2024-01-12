package sideproject.puddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Match;
import sideproject.puddy.model.Person;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>{
    boolean existsBySenderAndReceiver(Person sender, Person receiver);


    // List<Match> findByGenderAndMatched(boolean matched, boolean gender);
    @Query(value = "SELECT p " +
            "FROM Person p " +
            "WHERE p.gender = :gender" )
//            "AND NOT EXISTS ( " +
//            "SELECT m FROM Match m " +
//            "WHERE m.receiver.id = p.id " +
//            "AND m.sender.id = :currentUserId) " +
//            "AND FUNCTION('ST_DISTANCE_SPHERE', FUNCTION('POINT', p.longitude, p.latitude), FUNCTION('POINT', :searchLongitude, :searchLatitude)) <= 3000"
//    )
    Page<Person> findNearPersonNotMatched(
//            @Param("currentUserId") Long currentUserId,
            @Param("gender") boolean gender,
//            @Param("searchLongitude") Double searchLongitude,
//            @Param("searchLatitude") Double searchLatitude,
            Pageable pageable
    );

//    @Query(value = "select p from Person p " +
//            "join Match m on p.id = m.receiver.id " +
//            "where p.gender = :gender " +
//            "and (m.id is not null and m.sender.id = :currentUserId) "
//    )
//    Page<Person> findMatchedPersonInfo(
//            @Param("currentUserId") Long currentUserId,
//            @Param("gender") boolean gender,
//            @Param("searchLongitude") Double searchLongitude,
//            @Param("searchLatitude") Double searchLatitude,
//            Pageable pageable
//    );


}
