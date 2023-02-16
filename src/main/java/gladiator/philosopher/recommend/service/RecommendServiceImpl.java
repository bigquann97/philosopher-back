package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.recommend.repository.RecommendRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

  private final static int COUNT_FOR_MAKE_THREAD = 2;
  private final RecommendRepository recommendRepository;
  private final ThreadService threadService;

  @Transactional
  public void createRecommendPost(final Post post, final Account account) {
    checkIfUserAlreadyLiked(post, account);
    Recommend recommend = new Recommend(account, post);
    recommendRepository.save(recommend);
    makeThreadIfRecommendCountSatisfied(post);
  }

  @Transactional
  public void deleteRecommendPost(final Post post, final Account account) {
    Recommend recommend = recommendRepository.findByPostAndAccount(post, account)
        .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));
    recommendRepository.delete(recommend);
  }

  @Transactional
  public void createRecommendThread(final Thread thread, final Account account) {
    checkIfUserAlreadyLiked(thread, account);
    Recommend recommend = new Recommend(thread, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendThread(final Thread thread, final Account account) {
    Recommend recommend = recommendRepository.findByThreadAndAccount(thread,
        account).orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));
    recommendRepository.delete(recommend);
  }

  @Transactional
  public void createRecommendComment(final Comment comment, final Account account) {
    checkIfUserAlreadyLiked(comment, account);
    Recommend recommend = new Recommend(comment, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendComment(final Comment comment, final Account account) {
    Recommend recommend = recommendRepository.findByCommentAndAccount(comment,
        account).orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));
    recommendRepository.delete(recommend);
  }

  @Override
  @Transactional
  public long getPostRecommendCount(final Post post) {
    return recommendRepository.countByPost(post);
  }

  private void checkIfUserAlreadyLiked(final Object obj, final Account account) {
    if (obj instanceof Post) {
      Post post = (Post) obj;
      if (recommendRepository.existsByPostAndAccount(post, account)) {
        throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
      }
    } else if (obj instanceof Thread) {
      Thread thread = (Thread) obj;
      if (recommendRepository.existsByThreadAndAccount(thread, account)) {
        throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
      }
    } else if (obj instanceof Comment) {
      Comment comment = (Comment) obj;
      if (recommendRepository.existsByCommentAndAccount(comment, account)) {
        throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
      }
    } else {
      throw new IllegalArgumentException("잘못된 접근입니다.");
    }
  }

  @Transactional
  public void makeThreadIfRecommendCountSatisfied(final Post post) {
    if (getPostRecommendCount(post) >= COUNT_FOR_MAKE_THREAD) {
      threadService.startThread(post);
    }
  }

}
