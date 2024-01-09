package sideproject.puddy.dto.post.response;

import lombok.Data;
import sideproject.puddy.dto.person.response.PersonProfileDto;

import java.util.List;

@Data
public class PostDetailResponse {
    private Long id;
    private PersonProfileDto person;
    private String title;
    private String content;
    private String createdAt;
    private List<Like> likes;
    private List<Comment> comments;
    private boolean isMine;
}

@Data
class Like {
    private boolean isMine;
}

@Data
class Comment {
    private PersonProfileDto person;
    private String content;
    private String createdAt;
    private boolean isMine;
}