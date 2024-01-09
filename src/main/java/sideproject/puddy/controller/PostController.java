package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.dto.post.request.PostRequest;
import sideproject.puddy.dto.post.response.PostDetailResponse;
import sideproject.puddy.service.PostService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostRequest postCreateRequest) {
        return postService.savePost(postCreateRequest);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostRequest updateRequest, @PathVariable Long postId) {
        return postService.updatePost(updateRequest, postId);
    }

}
