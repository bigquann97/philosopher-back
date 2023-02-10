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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public void createRecommendPost(@PathVariable Long postId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    Post post = postService.getPostEntity(postId);
    recommendService.createRecommendPost(post, accountDetails.getAccount());
  }

  @DeleteMapping("/post/{postId}")
  public void deleteRecommendPost(@PathVariable Long postId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    Post post = postService.getPostEntity(postId);
    recommendService.deleteRecommendPost(post, accountDetails.getAccount());
  }

  @PostMapping("/thread/{threadId}")
  public void createRecommendThread(@PathVariable Long threadId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    Thread thread = threadService.getThreadEntity(threadId);
    recommendService.createRecommendThread(thread, accountDetails.getAccount());
  }

  @DeleteMapping("/thread/{threadId}")
  public void deleteRecommendThread(@PathVariable Long threadId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    Thread thread = threadService.getThreadEntity(threadId);
    recommendService.deleteRecommendThread(thread, accountDetails.getAccount());
  }

  @PostMapping("/comment/{commentId}")
  public void createRecommendComment(@PathVariable Long commentId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    Comment comment = commentService.getCommentEntity(commentId);
    recommendService.createRecommendComment(comment, accountDetails.getAccount());
  }

  @DeleteMapping("/comment/{commentId}")
  public void deleteRecommendComment(@PathVariable Long commentId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    Comment comment = commentService.getCommentEntity(commentId);
    recommendService.deleteRecommendComment(comment, accountDetails.getAccount());
  }
}
