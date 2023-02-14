package gladiator.philosopher.comment.controller;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
  private final ThreadService threadService;

  /**
   * threadId 로 전체 comment 조회
   *
   * @param threadId
   * @return
   */
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/api/{threadId}/comment")
  public List<CommentResponseDto> getComments(final @PathVariable Long threadId) {
    Thread thread = threadService.getThreadEntity(threadId);
    return commentService.getComments(thread);
  }

  /**
   * comment 등록
   *
   * @param commentRequestDto
   * @param threadId
   * @param accountDetails
   * @return
   */
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/api/{threadId}/comment")
  public void createComment(
      final @RequestBody CommentRequestDto commentRequestDto,
      final @PathVariable Long threadId,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    Thread thread = threadService.getThreadEntity(threadId);
    commentService.createComment(commentRequestDto, thread, accountDetails.getAccount());
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/api/comment/{commentId}")
  public void modifyComment(
      final @RequestBody CommentRequestDto commentRequestDto,
      final @PathVariable Long commentId,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    commentService.modifyComment(commentRequestDto, commentId, accountDetails.getAccount());
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/api/comment/{commentId}")
  public void deleteComment(
      final @PathVariable Long commentId,
      final CommentRequestDto commentRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    commentService.deleteComment(commentRequestDto, commentId, accountDetails.getAccount());
  }

}
