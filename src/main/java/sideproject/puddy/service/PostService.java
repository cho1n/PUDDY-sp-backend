package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.puddy.dto.comment.response.CommentDto;
import sideproject.puddy.dto.person.response.PersonProfileDto;
import sideproject.puddy.dto.post.request.PostRequest;
import sideproject.puddy.dto.post.response.PostDetailResponse;
import sideproject.puddy.dto.post.response.PostListResponse;
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
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final DogService dogService;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public ResponseEntity<String> savePost(PostRequest createRequest) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        postRepository.save(new Post(createRequest.getTitle(), createRequest.getContent(), person));
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> updatePost(PostRequest updateRequest, Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.updatePost(updateRequest.getTitle(), updateRequest.getContent());
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> deletePost(Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
        return ResponseEntity.ok().body("ok");
    }

    public ResponseEntity<PostDetailResponse> readPost(Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<CommentDto> commentList = post.getComments().stream()
                .map(comment -> CommentDto.builder()
                        .person(new PersonProfileDto(comment.getPerson().isGender(), dogService.findByPersonAndMain(comment.getPerson())))
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt().toString())
                        .isMine(comment.getPerson().equals(person))
                        .build()
                )
                .toList();

        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(post.getId())
                .person(new PersonProfileDto(post.getPerson().isGender(), dogService.findByPersonAndMain(post.getPerson())))
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

    public ResponseEntity<List<Post>> postList(PageRequest pageRequest, int pageNum) {
        List<Post> postList = postRepository.findAll(pageRequest).getContent();
        return ResponseEntity.ok().body(postList);
    }
}