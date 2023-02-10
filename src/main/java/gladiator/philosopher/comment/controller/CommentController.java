package gladiator.philosopher.comment.controller;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  /*
   * postId, threadId 로 전체 comment 조회
   *
   * @param postId
   * @param threadId
   * @return
   */
  @GetMapping("/api/posts/{postId}/{threadId}/comment")
  public List<CommentResponseDto> getComments(@PathVariable Long postId,
      @PathVariable Long threadId) {
    return commentService.getComments(postId, threadId);
  }

  /*
   * comment 등록
   *
   * @param commentRequestDto
   * @param postId
   * @param threadId
   * @param memberDetails
   * @return
   */
  @PostMapping("/api/posts/{postId}/{threadId}/comment")
  public ResponseEntity<CommentResponseDto> createComment(
      @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postId,
      @PathVariable Long threadId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    return ResponseEntity.status(200)
        .body(commentService.createComment(commentRequestDto, postId, threadId, memberDetails));
  }

  /*
   * postId, commentId 로 comment 수정
   *
   * @param commentRequestDto
   * @param postId
   * @param threadId
   * @param commentId
   * @param memberDetails
   * @return
   */
  @PutMapping("/api/posts/{postId}/{threadId}/comment/{commentId}")
  public ResponseEntity<CommentResponseDto> modifyComment(
      @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postId,
      @PathVariable Long threadId,
      @PathVariable Long commentId, @AuthenticationPrincipal MemberDetails memberDetails) {
    return ResponseEntity.status(200)
        .body(commentService.modifyComment(commentRequestDto, postId, threadId, commentId,
            memberDetails));
  }

  /*
   * postId, commentId 로 comment 삭제
   *
   * @param postId
   * @param threadId
   * @param commentId
   * @param memberDetails
   * @return
   */
  @DeleteMapping("/api/posts/{postId}/{threadId}/comment/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable Long postId,
      @PathVariable Long threadId,
      @PathVariable Long commentId, @AuthenticationPrincipal MemberDetails memberDetails) {
    commentService.deleteComment(postId, threadId, commentId, memberDetails);
    return ResponseEntity.status(200).body("삭제 완료");
  }
}
