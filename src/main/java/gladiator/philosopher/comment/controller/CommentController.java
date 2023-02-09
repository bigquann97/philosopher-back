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
   * postId 로 전체 comment 조회
   *
   * @param postId
   * @param memberDetails
   * @return
   */
  @GetMapping("/api/posts/{postId}/comment")
  public List<CommentResponseDto> getComments(@PathVariable Long postId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    return commentService.getComments(postId, memberDetails);
  }

  /*
   * comment 등록
   *
   * @param commentRequestDto
   * @param postId
   * @param memberDetails
   * @return
   */
  @PostMapping("/api/posts/{postId}/comment")
  public ResponseEntity<CommentResponseDto> createComment(
      @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    return ResponseEntity.status(200)
        .body(commentService.createComment(commentRequestDto, postId, memberDetails));
  }

  /*
   * postId, commentId 로 comment 수정
   *
   * @param commentRequestDto
   * @param commentId
   * @param postId
   * @param memberDetails
   * @return
   */
  @PutMapping("/api/posts/{postId}/comment/{commentId}")
  public ResponseEntity<CommentResponseDto> modifyComment(
      @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long commentId,
      @PathVariable Long postId, @AuthenticationPrincipal MemberDetails memberDetails) {
    return ResponseEntity.status(200)
        .body(commentService.modifyComment(commentRequestDto, commentId, postId, memberDetails));
  }

  /*
   * postId, commentId 로 comment 삭제
   *
   * @param postId
   * @param commentId
   * @param memberDetails
   * @return
   */
  @DeleteMapping("/api/posts/{postId}/comment/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable Long postId,
      @PathVariable Long commentId, @AuthenticationPrincipal MemberDetails memberDetails) {
    commentService.deleteComment(postId, commentId, memberDetails);
    return ResponseEntity.status(200).body("삭제 완료");
  }
}
