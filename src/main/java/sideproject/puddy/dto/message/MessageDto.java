package sideproject.puddy.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sideproject.puddy.model.ChatMessage;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private Long chatId;
    private String content;
    private Long senderId;
    private LocalDate createdAt;

    public MessageDto(ChatMessage message){
        this.id = message.getId();
        this.chatId = message.getChat().getId();
        this.content = message.getContent();
        this.senderId = message.getSender().getId();
        this.createdAt = message.getCreatedAt();
    }
}
