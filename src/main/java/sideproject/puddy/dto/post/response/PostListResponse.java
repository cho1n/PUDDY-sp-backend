package sideproject.puddy.dto.post.response;

import lombok.Data;
import sideproject.puddy.dto.person.response.PersonProfileDto;

import java.util.List;

@Data
public class PostListResponse {
    private Long count;
    private List<PostResponse> posts;
}

@Data
class PostResponse {
    private Long id;
    private PersonProfileDto person;
    private String title;
    private String content;
    private String createdAt;
    private Integer likeCount;
    private Integer commentCount;
    private boolean isMine;
}
