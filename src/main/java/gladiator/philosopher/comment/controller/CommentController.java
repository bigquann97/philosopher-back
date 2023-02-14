package gladiator.philosopher.comment.controller;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
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
  @GetMapping("/thread/{threadId}")
  public Page<CommentResponseDto> selectCommentsWithPaging(
      final @PathVariable Long threadId,
      final @RequestParam(required = false, defaultValue = "1") int page
  ) {
    Thread thread = threadService.getThreadEntity(threadId);
    return commentService.selectCommentsWithPaging(thread, page - 1);
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
  @PostMapping("/thread/{threadId}")
  public void createComment(
      final @RequestBody CommentRequestDto commentRequestDto,
      final @PathVariable Long threadId,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    Thread thread = threadService.getThreadEntity(threadId);
    commentService.createComment(commentRequestDto, thread, accountDetails.getAccount());
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{commentId}")
  public void modifyComment(
      final @RequestBody CommentRequestDto commentRequestDto,
      final @PathVariable Long commentId,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    commentService.modifyComment(commentRequestDto, commentId, accountDetails.getAccount());
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{commentId}")
  public void deleteComment(
      final @PathVariable Long commentId,
      final CommentRequestDto commentRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    commentService.deleteComment(commentRequestDto, commentId, accountDetails.getAccount());
  }

}
