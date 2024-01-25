package sideproject.puddy.repository.redis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatIdAndPersonId(Long chatId, Long personId);
}
