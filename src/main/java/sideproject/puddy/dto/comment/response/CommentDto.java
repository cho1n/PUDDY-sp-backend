package sideproject.puddy.dto.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import sideproject.puddy.dto.person.response.PersonProfileDto;

@Data
@Builder
public class CommentDto {
    private Long id;
    private PersonProfileDto person;
    private String content;
    private String createdAt;
    @JsonProperty("isMine")
    private boolean isMine;
}
