package sideproject.puddy.repository;

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

    @Query(value = "SELECT p " +
            "FROM Person p " +
            "WHERE p.gender = :gender " +
            "AND NOT EXISTS ( " +
            "SELECT m FROM Match m " +
            "WHERE m.receiver.id = p.id " +
            "AND m.sender.id = :currentUserId) " +
            "AND FUNCTION('ST_DISTANCE_SPHERE', FUNCTION('POINT', p.longitude, p.latitude), FUNCTION('POINT', :searchLongitude, :searchLatitude)) <= 3000" +
            "ORDER BY RAND()" +
            "LIMIT 10"
    )
    List<Person> findNearPersonNotMatched(
            @Param("currentUserId") Long currentUserId,
            @Param("gender") boolean gender,
            @Param("searchLongitude") Double searchLongitude,
            @Param("searchLatitude") Double searchLatitude
    );

    List<Match> findByReceiverId(Long id);
    List<Match> findByReceiverIdOrSenderId(Long senderId, Long receiverId);
}
