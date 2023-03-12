package gladiator.philosopher.comment.controller;

import gladiator.philosopher.comment.dto.CommentOpinionStatsDto;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.dto.FavCommentResponseDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
   * 쓰레드{id} 댓글 조회 by 페이징
   * @param threadId
   * @param page
   * @return
   */
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/thread/{threadId}")
  public MyPage<CommentResponseDto> selectCommentsWithPaging(
      final @PathVariable Long threadId,
      final @RequestParam(required = false, defaultValue = "1") int page
  ) {
    return commentService.selectCommentsWithPaging(threadId, page - 1);
  }

  /**
   * 댓글 작성
   * @param commentRequestDto
   * @param threadId
   * @param accountDetails
   */
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/thread/{threadId}")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void createComment(
      final @RequestBody CommentRequestDto commentRequestDto,
      final @PathVariable Long threadId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    Thread thread = threadService.getThreadEntity(threadId);
    commentService.createComment(commentRequestDto, thread, accountDetails.getAccount());
  }

  /**
   * 댓글 수정
   * @param commentRequestDto
   * @param commentId
   * @param accountDetails
   */
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{commentId}")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void modifyComment(
      final @RequestBody CommentRequestDto commentRequestDto,
      final @PathVariable Long commentId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    commentService.modifyComment(commentRequestDto, commentId, accountDetails.getAccount());
  }

  /**
   * 댓글 삭제
   * @param commentId
   * @param accountDetails
   */
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{commentId}")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void deleteComment(
      final @PathVariable Long commentId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    commentService.deleteComment(commentId, accountDetails.getAccount());
  }

  /**
   * 댓글 비율 및 상태 조회
   * @param threadId
   * @return
   */
  @GetMapping("/stat/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  public List<CommentOpinionStatsDto> selectStats(final @PathVariable Long threadId) {
    return commentService.selectStatistics(threadId);
  }

  /**
   * 가장 많이 좋아요 받은 댓글 조회
   * @param threadId
   * @return
   */
  @GetMapping("/favorite/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  public List<FavCommentResponseDto> selectFavComments(final @PathVariable Long threadId) {
    return commentService.selectFavComments(threadId);
  }

}
