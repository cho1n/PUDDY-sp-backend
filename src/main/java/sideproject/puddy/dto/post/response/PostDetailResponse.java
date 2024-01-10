package sideproject.puddy.dto.post.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import sideproject.puddy.dto.comment.response.CommentDto;
import sideproject.puddy.dto.person.response.PersonProfileDto;

import java.util.List;

@Data
@Builder
public class PostDetailResponse {
    private Long id;
    private PersonProfileDto person;
    private String title;
    private String content;
    private String createdAt;
    private boolean isLike;
    private Integer likeCount;
    private List<CommentDto> comments;
    private boolean isMine;
}