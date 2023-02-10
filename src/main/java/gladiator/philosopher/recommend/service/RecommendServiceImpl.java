package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.recommend.repository.RecommendRepository;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

  private final RecommendRepository recommendRepository;

  @Transactional
  public void createRecommendPost(Post post, Account account) {
    Optional<Recommend> recommendPost = recommendRepository.findByPostIdAndAccount(post.getId(),
        account);
    if (recommendPost.isPresent()) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(post, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendPost(Post post, Account account) {
    Optional<Recommend> recommendPost = recommendRepository.findByPostIdAndAccount(post.getId(),
        account);
    if (recommendPost.isEmpty()) {
      throw new IllegalArgumentException("이미 좋아요 취소를 하셨습니다.");
    }
    recommendRepository.delete(recommendPost.get());
  }

  @Transactional
  public void createRecommendThread(Thread thread, Account account) {
    Optional<Recommend> recommendThread = recommendRepository.findByThreadIdAndAccount(
        thread.getId(),
        account);
    if (recommendThread.isPresent()) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(thread, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendThread(Thread thread, Account account) {
    Optional<Recommend> recommendThread = recommendRepository.findByThreadIdAndAccount(
        thread.getId(),
        account);
    if (recommendThread.isEmpty()) {
      throw new IllegalArgumentException("이미 좋아요 취소를 하셨습니다.");
    }
    recommendRepository.delete(recommendThread.get());
  }

  @Transactional
  public void createRecommendComment(Comment comment, Account account) {
    Optional<Recommend> recommendComment = recommendRepository.findByCommentIdAndAccount(
        comment.getId(),
        account);
    if (recommendComment.isPresent()) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(comment, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendComment(Comment comment, Account account) {
    Optional<Recommend> recommendComment = recommendRepository.findByCommentIdAndAccount(
        comment.getId(),
        account);
    if (recommendComment.isEmpty()) {
      throw new IllegalArgumentException("이미 좋아요 취소를 하셨습니다.");
    }
    recommendRepository.delete(recommendComment.get());
  }

/*
  public Long countRecommendPost(Long postId) {
    Long countRecommendPost = recommendRepository.countByPostId(postId);
    return countRecommendPost;
  }
*/

  @Override
  public List<Recommend> getPostRecommends(Post post) {
    return recommendRepository.findByPost(post);
  }
}
