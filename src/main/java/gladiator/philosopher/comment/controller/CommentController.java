package gladiator.philosopher.comment.controller;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.security.AccountDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  /*
   * threadId 로 전체 comment 조회
   *
   * @param threadId
   * @return
   */
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/api/{threadId}/comment")
  public List<CommentResponseDto> getComments(@PathVariable Long threadId) {
    return commentService.getComments(threadId);
  }

  /*
   * comment 등록
   *
   * @param commentRequestDto
   * @param threadId
   * @param accountDetails
   * @return
   */
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/api/{threadId}/comment")
  public ResponseEntity<CommentResponseDto> createComment(
      @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long threadId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    return ResponseEntity.status(200)
        .body(commentService.createComment(commentRequestDto, threadId, accountDetails));
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/api/comment/{commentId}")
  public void modifyComment(@RequestBody CommentRequestDto commentRequestDto,
      @PathVariable Long commentId, @AuthenticationPrincipal AccountDetails accountDetails) {
    commentService.modifyComment(commentId, commentRequestDto.getContent());
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/api/comment/{commentId}")
  public void deleteComment(@PathVariable Long commentId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    commentService.deleteComment(commentId);
  }

}
