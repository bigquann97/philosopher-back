package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.recommend.entity.CommentRecommend;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.recommend.entity.ThreadRecommend;
import gladiator.philosopher.recommend.repository.CommentRecommendRepository;
import gladiator.philosopher.recommend.repository.PostRecommendRepository;
import gladiator.philosopher.recommend.repository.ThreadRecommendRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

  private final static int COUNT_FOR_MAKE_THREAD = 2;
  private final PostRecommendRepository postRecommendRepository;
  private final ThreadRecommendRepository threadRecommendRepository;
  private final CommentRecommendRepository commentRecommendRepository;
  private final ThreadService threadService;
  private final PostService postService;

  @Transactional
  public void createRecommendPost(final Post post, final Account account) {
    Post initializedPost = postService.getPostEntity(post.getId());
    checkIfUserAlreadyLiked(initializedPost, account);
    PostRecommend postRecommend = new PostRecommend(account, initializedPost);
    postRecommendRepository.save(postRecommend);
    makeThreadIfRecommendCountSatisfied(initializedPost);
  }

  @Transactional
  public void deleteRecommendPost(final Post post, final Account account) {
    PostRecommend postRecommend = postRecommendRepository.findByPostAndAccount(post, account)
        .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));
    postRecommendRepository.delete(postRecommend);
  }

  @Transactional
  public void createRecommendThread(final Thread thread, final Account account) {
    checkIfUserAlreadyLiked(thread, account);
    ThreadRecommend recommend = new ThreadRecommend(account, thread);
    threadRecommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendThread(final Thread thread, final Account account) {
    ThreadRecommend threadRecommend = threadRecommendRepository.findByThreadAndAccount(thread,
        account).orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));
    threadRecommendRepository.delete(threadRecommend);
  }

  @Transactional
  public void createRecommendComment(final Comment comment, final Account account) {
    checkIfUserAlreadyLiked(comment, account);
    CommentRecommend recommend = new CommentRecommend(account, comment);
    commentRecommendRepository.save(recommend);
  }

  @Transactional
  public void deleteRecommendComment(final Comment comment, final Account account) {
    CommentRecommend commentRecommend = commentRecommendRepository.findByCommentAndAccount(comment,
        account).orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));
    commentRecommendRepository.delete(commentRecommend);
  }

  @Override
  @Transactional
  public long getPostRecommendCount(final Post post) {
    return postRecommendRepository.countByPost(post);
  }

  private void checkIfUserAlreadyLiked(final Object obj, final Account account) {
    if (obj instanceof Post) {
      Post post = (Post) obj;
      if (postRecommendRepository.existsByPostAndAccount(post, account)) {
        throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
      }
    } else if (obj instanceof Thread) {
      Thread thread = (Thread) obj;
      if (threadRecommendRepository.existsByThreadAndAccount(thread, account)) {
        throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
      }
    } else if (obj instanceof Comment) {
      Comment comment = (Comment) obj;
      if (commentRecommendRepository.existsByCommentAndAccount(comment, account)) {
        throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
      }
    } else {
      throw new IllegalArgumentException("잘못된 접근입니다.");
    }
  }

  @Transactional
  public void makeThreadIfRecommendCountSatisfied(final Post post) {
    if (post.isThreaded()) { // 이미 쓰레드화 되어있으면 종료
      return;
    }

    if (getPostRecommendCount(post) >= COUNT_FOR_MAKE_THREAD) {
      post.makeThread();
      threadService.startThread(post);
    }
  }

}
