package gladiator.philosopher.recommend.controller;

import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.recommend.dto.RecommendResponseDto;
import gladiator.philosopher.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interests")
public class RecommendController {

  private final RecommendService recommendService;

  @PostMapping("/post/{postId}")
  public RecommendResponseDto createRecommendPost(@PathVariable Long postId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    recommendService.createRecommendPost(postId, accountDetails.getAccount());
    return new RecommendResponseDto("좋아요");
  }

  @DeleteMapping("/post/{postId}")
  public RecommendResponseDto deleteRecommendPost(@PathVariable Long postId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    recommendService.deleteRecommendPost(postId, accountDetails.getAccount());
    return new RecommendResponseDto("좋아요 취소");
  }

  @PostMapping("/thread/{threadId}")
  public RecommendResponseDto createRecommendThread(@PathVariable Long threadId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    recommendService.createRecommendThread(threadId, accountDetails.getAccount());
    return new RecommendResponseDto("좋아요");
  }

  @DeleteMapping("/thread/{threadId}")
  public RecommendResponseDto deleteRecommendThread(@PathVariable Long threadId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    recommendService.deleteRecommendThread(threadId, accountDetails.getAccount());
    return new RecommendResponseDto("좋아요 취소");
  }

  @PostMapping("/comment/{commentId}")
  public RecommendResponseDto createRecommendComment(@PathVariable Long commentId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    recommendService.createRecommendComment(commentId, accountDetails.getAccount());
    return new RecommendResponseDto("좋아요");
  }

  @DeleteMapping("/comment/{commentId}")
  public RecommendResponseDto deleteRecommendComment(@PathVariable Long commentId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    recommendService.deleteRecommendComment(commentId, accountDetails.getAccount());
    return new RecommendResponseDto("좋아요 취소");
  }
}
