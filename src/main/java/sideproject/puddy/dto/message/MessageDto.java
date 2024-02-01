package sideproject.puddy.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sideproject.puddy.model.ChatMessage;

@Getter
@AllArgsConstructor
public class MessageDto {
    private String content;
    private Long chatId;
    private Long senderId;

    public MessageDto(ChatMessage message){
        this.content = message.getContent();
        this.chatId = message.getChatId();
        this.senderId = message.getSender().getId();
    }
}
