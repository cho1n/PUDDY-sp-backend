package sideproject.puddy.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostListResponse {
    private Long count;
    private List<PostResponseDto> posts;
}