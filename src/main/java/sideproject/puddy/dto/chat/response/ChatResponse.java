package sideproject.puddy.dto.chat.response;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponse {
    private Long id;
    @Nullable
    private ChatPerson person;
}
