package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.puddy.dto.comment.request.CommentRequest;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Comment;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Post;
import sideproject.puddy.repository.CommentRepository;
import sideproject.puddy.repository.PostRepository;
import sideproject.puddy.security.util.SecurityUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final AuthService authService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<String> createComment(CommentRequest commentRequest, Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        commentRepository.save(new Comment(commentRequest.getContent(), person, post));
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> updateComment(CommentRequest commentRequest, Long postId, Long commentId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.updateComment(commentRequest.getContent());
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long postId, Long commentId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);

        return ResponseEntity.ok().body("ok");
    }
}
