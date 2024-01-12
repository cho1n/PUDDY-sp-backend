package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.puddy.dto.person.response.PersonProfileDto;
import sideproject.puddy.dto.post.request.PostRequest;
import sideproject.puddy.dto.post.response.PostListResponse;
import sideproject.puddy.dto.post.response.PostResponseDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Post;
import sideproject.puddy.model.PostLike;
import sideproject.puddy.repository.PostLikeRepository;
import sideproject.puddy.repository.PostRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final AuthService authService;
    private final DogService dogService;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public ResponseEntity<String> savePost(PostRequest createRequest) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        postRepository.save(new Post(createRequest.getTitle(), createRequest.getContent(), person));
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> postLike(Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        boolean isLike = postLikeRepository.existsByPostAndPerson(post, person);
        if(!isLike)
            postLikeRepository.save(new PostLike(person, post));
        else {
            PostLike postlike = postLikeRepository.findByPostAndPerson(post, person);
            postLikeRepository.delete(postlike);
        }

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

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public ResponseEntity<PostListResponse> postList(PageRequest pageRequest) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());

        List<PostResponseDto> postList = postRepository.findAll(pageRequest).stream()
                .map(post -> PostResponseDto.builder()
                        .id(post.getId())
                        .person(new PersonProfileDto(post.getPerson().isGender(), dogService.findByPersonAndMain(post.getPerson())))
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt().toString())
                        .likeCount(post.getPostLikes().size())
                        .commentCount(post.getComments().size())
                        .isMine(post.getPerson().equals(person))
                        .build()
                )
                .toList();
        PostListResponse postListResponse = new PostListResponse((long)pageRequest.getPageNumber()+1, postList);

        return ResponseEntity.ok().body(postListResponse);
    }
}