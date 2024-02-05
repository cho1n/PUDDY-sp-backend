package sideproject.puddy.dto.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sideproject.puddy.model.ChatMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class MessageDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("chatId")
    private Long chatId;
    @JsonProperty("content")
    private String content;
    @JsonProperty("senderId")
    private Long senderId;

    public MessageDto(ChatMessage message){
        this.id = message.getId();
        this.chatId = message.getChat().getId();
        this.content = message.getContent();
        this.senderId = message.getSender().getId();
    }
}
