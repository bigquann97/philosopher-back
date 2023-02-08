package gladiator.philosopher.recommend.controller;

import gladiator.philosopher.recommend.dto.RecommendResponseDto;
import gladiator.philosopher.recommend.service.RecommendService;
import gladiator.philosopher.security.members.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public RecommendResponseDto modifyRecommendPost(@PathVariable Long postId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    recommendService.modifyRecommendPost(postId, memberDetails.getMember());
    return new RecommendResponseDto("좋아요");
  }
  /*
  @PostMapping("/post/{commentId}")
  public RecommendResponseDto modifyRecommendComment(@PathVariable Long commentId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    recommendService.modifyRecommendComment(commentId, memberDetails.getMember());
    return new RecommendResponseDto("좋아요");
  }
*/
}
