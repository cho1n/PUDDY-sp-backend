package sideproject.puddy.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponse {
    private Long id;
    private Long personId;
    private boolean gender;
    private ChatDogResponse dog;
}
