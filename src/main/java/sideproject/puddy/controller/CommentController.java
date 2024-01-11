package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.dto.comment.request.CommentRequest;
import sideproject.puddy.service.CommentService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> createComment(@RequestBody CommentRequest commentRequest, @PathVariable Long postId) {
        return commentService.createComment(commentRequest, postId);
    }

    @PatchMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<String> updateComment(
            @RequestBody CommentRequest commentRequest,
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        return commentService.updateComment(commentRequest, postId, commentId);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return commentService.deleteComment(postId, commentId);
    }
}
