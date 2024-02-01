package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.puddy.dto.comment.request.CommentRequest;
import sideproject.puddy.dto.comment.response.CommentDto;
import sideproject.puddy.dto.person.response.PersonProfileDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Comment;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Post;
import sideproject.puddy.repository.CommentRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final AuthService authService;
    private final DogService dogService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<String> createComment(CommentRequest commentRequest, Long postId) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Post post = postService.getPost(postId);
        commentRepository.save(new Comment(commentRequest.getContent(), person, post));
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> updateComment(CommentRequest commentRequest, Long postId, Long commentId) {
        Post post = postService.getPost(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.updateComment(commentRequest.getContent());
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long postId, Long commentId) {
        Post post = postService.getPost(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);

        return ResponseEntity.ok().body("ok");
    }

    public List<CommentDto> getCommentsInPost (Person person, Post post) {

        return post.getComments().stream()
                .map(comment -> CommentDto.builder()
                        .person(new PersonProfileDto(comment.getPerson().isGender(), dogService.findProfileByPersonAndMain(comment.getPerson())))
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt().toString())
                        .isMine(comment.getPerson().equals(person))
                        .build()
                )
                .toList();
    }
}
