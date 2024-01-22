package sideproject.puddy.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetChatListResonse {
    private List<ChatDto> persons;
}
