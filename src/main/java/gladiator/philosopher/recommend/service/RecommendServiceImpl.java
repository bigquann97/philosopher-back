package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.recommend.repository.RecommendRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

  private final RecommendRepository recommendRepository;
  private final PostService postService;   // Impl말고 Service를 참조해야하게 수정필요
  private final CommentService commentService;
  //  private final ThreadService threadService;
  private final ThreadRepository threadRepository;

  @Transactional
  public void createRecommendPost(Long postId, Account account) {
    Post post = postService.getPostEntity(postId);
    Optional<Recommend> recommendPost = recommendRepository.findByPostIdAndAccount(postId, account);
    if (recommendPost.isPresent()) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(post, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendPost(Long postId, Account account) {
    Optional<Recommend> recommendPost = recommendRepository.findByPostIdAndAccount(postId, account);
    if (recommendPost.isEmpty()) {
      throw new IllegalArgumentException("이미 좋아요 취소를 하셨습니다.");
    }
    recommendRepository.delete(recommendPost.get());
  }

  @Transactional
  public void createRecommendThread(Long threadId, Account account) {
//    Thread thread = threadService.getThreadEntity(threadId);
    Thread thread = threadRepository.findById(threadId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    Optional<Recommend> recommendThread = recommendRepository.findByThreadIdAndAccount(threadId,
        account);
    if (recommendThread.isPresent()) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(thread, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendThread(Long threadId, Account account) {
    Optional<Recommend> recommendThread = recommendRepository.findByThreadIdAndAccount(threadId,
        account);
    if (recommendThread.isEmpty()) {
      throw new IllegalArgumentException("이미 좋아요 취소를 하셨습니다.");
    }
    recommendRepository.delete(recommendThread.get());
  }

  @Transactional
  public void createRecommendComment(Long commentId, Account account) {
    Comment comment = commentService.getCommentEntity(commentId);
    Optional<Recommend> recommendComment = recommendRepository.findByCommentIdAndAccount(commentId,
        account);
    if (recommendComment.isPresent()) {
      throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
    }
    Recommend recommend = new Recommend(comment, account);
    recommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendComment(Long commentId, Account account) {
    Optional<Recommend> recommendComment = recommendRepository.findByCommentIdAndAccount(commentId,
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
