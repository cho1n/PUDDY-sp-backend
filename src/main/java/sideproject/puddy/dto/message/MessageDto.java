package sideproject.puddy.dto.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sideproject.puddy.model.ChatMessage;

import java.time.temporal.ChronoUnit;

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
    @JsonProperty("date")
    private String date;
    @JsonProperty("time")
    private String time;

    public MessageDto(ChatMessage message){
        this.id = message.getId();
        this.chatId = message.getChat().getId();
        this.content = message.getContent();
        this.senderId = message.getSender().getId();
        this.date = message.getCreatedAt().toLocalDate().toString();
        this.time = message.getCreatedAt().toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString();
    }
}
