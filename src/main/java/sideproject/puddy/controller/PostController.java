package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.dto.post.request.PostRequest;
import sideproject.puddy.dto.post.response.PostDetailResponse;
import sideproject.puddy.dto.post.response.PostListResponse;
import sideproject.puddy.model.Post;
import sideproject.puddy.service.PostService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostRequest createRequest) {
        return postService.savePost(createRequest);
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostRequest updateRequest, @PathVariable Long postId) {
        return postService.updatePost(updateRequest, postId);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDetailResponse> readPost(@PathVariable Long postId) {
        return postService.readPost(postId);
    }

    @GetMapping("/post")
    public ResponseEntity<List<Post>> pageList(@RequestParam int pageNum){
        PageRequest pageRequest = PageRequest.of(pageNum - 1, 10);
        return postService.postList(pageRequest, pageNum);
    }
}
