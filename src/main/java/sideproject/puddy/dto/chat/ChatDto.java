package sideproject.puddy.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sideproject.puddy.model.Chat;

import java.io.Serial;
import java.io.Serializable;
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ChatDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6494678977089006639L;      // 역직렬화 위한 serialVersionUID 세팅
    private String firstPersonId;
    private String secondPersonId;
    public ChatDto(Chat chat){
        this.firstPersonId = chat.getFirstPerson().getId().toString();
        this.secondPersonId = chat.getSecondPerson().getId().toString();
    }

}
