package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.comment.response.CommentDto;
import sideproject.puddy.dto.person.response.PersonProfileDto;
import sideproject.puddy.dto.post.response.PostDetailResponse;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Post;
import sideproject.puddy.repository.PostLikeRepository;
import sideproject.puddy.repository.PostRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostAndCommentService {

    public final AuthService authService;
    public final DogService dogService;
    public final PostService postService;
    public final CommentService commentService;
    public final PostRepository postRepository;
    public final PostLikeRepository postLikeRepository;

    public ResponseEntity<PostDetailResponse> readPost(Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        List<CommentDto> commentList = commentService.getCommentsInPost(person, post);

        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(post.getId())
                .person(new PersonProfileDto(post.getPerson().isGender(), dogService.findProfileByPersonAndMain(post.getPerson())))
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt().toString())
                .isLike(postLikeRepository.existsByPostAndPerson(post, person))
                .likeCount(post.getPostLikes().size())
                .comments(commentList)
                .isMine(post.getPerson().equals(person))
                .build();

        return ResponseEntity.ok().body(postDetailResponse);
    }
}
