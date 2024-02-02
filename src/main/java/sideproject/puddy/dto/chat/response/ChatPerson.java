package sideproject.puddy.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatPerson {
    private boolean gender;
    private ChatDogResponse dog;
}
