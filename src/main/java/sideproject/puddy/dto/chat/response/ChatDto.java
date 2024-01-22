package sideproject.puddy.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatDto {
    private Long id;
    private Long personId;
    private boolean gender;
    private ChatDogDto dog;
}
