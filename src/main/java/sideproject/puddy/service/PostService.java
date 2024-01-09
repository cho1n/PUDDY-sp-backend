package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.puddy.dto.post.request.PostRequest;
import sideproject.puddy.dto.post.response.PostDetailResponse;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Post;
import sideproject.puddy.repository.PostRepository;
import sideproject.puddy.security.util.SecurityUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public ResponseEntity<String> savePost(PostRequest postCreateRequest) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        postRepository.save(new Post(postCreateRequest.getTitle(), postCreateRequest.getContent(), person));
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> deletePost(Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        if(!post.getPerson().equals(person)) throw new CustomException(ErrorCode.USER_NOT_MATCH);
        postRepository.delete(post);
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> updatePost(PostRequest updateRequest, Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        if(!post.getPerson().equals(person)) throw new CustomException(ErrorCode.USER_NOT_MATCH);
        post.updatePost(updateRequest.getTitle(), updateRequest.getContent());
        return ResponseEntity.ok().body("ok");
    }


}