package gladiator.philosopher.report.controller;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.service.ReportService;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

  private final ReportService reportService;
  private final PostService postService;
  private final ThreadService threadService;
  private final CommentService commentService;

  @PostMapping("/post")
  public void reportPost(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    Post post = postService.getPostEntity(id);
    reportService.reportPost(post, dto, accountDetails.getAccount());
  }

  @PostMapping("/comment")
  public void reportComment(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    Comment comment = commentService.getCommentEntity(id);
    reportService.reportComment(comment, dto, accountDetails.getAccount());
  }

  // api/report/thread?id=3
  @PostMapping("/thread")
  public void reportThread(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    Thread thread = threadService.getThreadEntity(id);
    reportService.reportThread(thread, dto, accountDetails.getAccount());
  }

}
