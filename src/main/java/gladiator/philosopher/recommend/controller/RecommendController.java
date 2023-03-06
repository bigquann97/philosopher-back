package gladiator.philosopher.recommend.controller;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.recommend.service.RecommendService;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendController {

  private final RecommendService recommendService;
  private final PostService postService;
  private final ThreadService threadService;
  private final CommentService commentService;

  @PostMapping("/post/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void createRecommendPost(
      final @PathVariable Long postId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    Post post = postService.getPostEntity(postId);
    recommendService.createRecommendPost(post, accountDetails.getAccount());
  }

  @DeleteMapping("/post/{postId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void deleteRecommendPost(
      final @PathVariable Long postId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    Post post = postService.getPostEntity(postId);
    recommendService.deleteRecommendPost(post, accountDetails.getAccount());
  }

  @PostMapping("/thread/{threadId}")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void createRecommendThread(
      final @PathVariable Long threadId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    Thread thread = threadService.getThreadEntity(threadId);
    recommendService.createRecommendThread(thread, accountDetails.getAccount());
  }

  @DeleteMapping("/thread/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void deleteRecommendThread(
      final @PathVariable Long threadId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    Thread thread = threadService.getThreadEntity(threadId);
    recommendService.deleteRecommendThread(thread, accountDetails.getAccount());
  }

  @PostMapping("/comment/{commentId}")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void createRecommendComment(
      final @PathVariable Long commentId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    Comment comment = commentService.getCommentEntity(commentId);
    recommendService.createRecommendComment(comment, accountDetails.getAccount());
  }

  @DeleteMapping("/comment/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void deleteRecommendComment(
      final @PathVariable Long commentId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    Comment comment = commentService.getCommentEntity(commentId);
    recommendService.deleteRecommendComment(comment, accountDetails.getAccount());
  }

}
