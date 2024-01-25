package sideproject.puddy.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "message")
public class Message {
    @Id
    private Long id;
    private String content;
    private Long chatId;
    private Long senderId;

    public Message(String content, Long chatId, Long senderId) {
        this.content = content;
        this.chatId = chatId;
        this.senderId = senderId;
    }
}
