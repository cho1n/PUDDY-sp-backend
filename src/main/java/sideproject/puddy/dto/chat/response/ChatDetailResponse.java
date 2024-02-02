package sideproject.puddy.dto.chat.response;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.message.MessageDto;


import java.util.List;

@AllArgsConstructor
@Data
public class ChatDetailResponse {
    private Long id;
    private Long currentUserId;
    @Nullable
    private ChatPerson person;
    private List<MessageDto> messages;
}
