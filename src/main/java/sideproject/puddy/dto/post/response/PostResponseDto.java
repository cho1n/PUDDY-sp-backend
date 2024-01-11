package sideproject.puddy.dto.post.response;

import lombok.Builder;
import lombok.Getter;
import sideproject.puddy.dto.person.response.PersonProfileDto;

@Builder
@Getter
public class PostResponseDto {
    private Long id;
    private PersonProfileDto person;
    private String title;
    private String content;
    private String createdAt;
    private Integer likeCount;
    private Integer commentCount;
    private boolean isMine;
}
