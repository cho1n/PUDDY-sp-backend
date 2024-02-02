package sideproject.puddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.ChatMessage;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatId(Long chatId);
}
