package sideproject.puddy.dto.message.request;

import lombok.Data;

@Data
public class PostMessageRequest {
    private Long chatId;
    private String content;
    private Long currentUserId;
}
