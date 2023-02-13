package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.recommend.repository.RecommendRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

  private final RecommendRepository recommendRepository;
  private final ThreadService ThreadService;

  @Transactional
  public void createRecommendPost(Post post, Account account) {
    if (recommendRepository.existsByPostAndAccount(post, account)) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(post, account);
    recommendRepository.save(recommend);
    if (getPostRecommends(post).size() >= 2) {
      ThreadService.startThread(post);
    }
  }

  @Transactional
  public void deleteRecommendPost(Post post, Account account) {
    Recommend recommend = recommendRepository.findByPostAndAccount(post,
        account).orElseThrow(() -> new IllegalArgumentException("이미 좋아요 취소를 하셨습니다."));
    recommendRepository.delete(recommend);
  }

  @Transactional
  public void createRecommendThread(Thread thread, Account account) {
    if (recommendRepository.existsByThreadAndAccount(thread, account)) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(thread, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendThread(Thread thread, Account account) {
    Recommend recommend = recommendRepository.findByThreadAndAccount(thread,
        account).orElseThrow(() -> new IllegalArgumentException("이미 좋아요 취소를 하셨습니다."));
    recommendRepository.delete(recommend);
  }

  @Transactional
  public void createRecommendComment(Comment comment, Account account) {
    if (recommendRepository.existsByCommentAndAccount(comment, account)) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(comment, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendComment(Comment comment, Account account) {
    Recommend recommend = recommendRepository.findByCommentAndAccount(comment,
        account).orElseThrow(() -> new IllegalArgumentException("이미 좋아요 취소를 하셨습니다."));
    recommendRepository.delete(recommend);
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
