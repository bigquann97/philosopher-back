package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.recommend.repository.RecommendRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService{

  private final RecommendRepository recommendRepository;
  private final PostRepository postRepository;


  @Transactional
  public void modifyRecommendPost(Long postId, Account account) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    Optional<Recommend> recommendPost = recommendRepository.findByPostIdAndAccount(postId, account);
    if (recommendPost.isEmpty()) {
      Recommend recommend = new Recommend(post, account);
      recommendRepository.save(recommend);
    } else {
      recommendRepository.delete(recommendPost.get());
    }
  }
/*
  @Transactional
  public void modifyRecommendComment(Long commentId, Account account) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    Optional<Recommend> recommendComment = recommendRepository.findByCommentIdAndAccount(commentId, account);
    if (recommendComment.isEmpty()) {
      Recommend recommend = new Recommend(comment, account);
      recommendRepository.save(recommend);
    } else {
      recommendRepository.delete(recommendComment.get());
    }
  }
  */
}
