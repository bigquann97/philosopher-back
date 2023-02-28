package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.dto.RecommendCommentResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.thread.entity.Thread;
import org.springframework.data.domain.Pageable;

public interface RecommendService {

  void createRecommendPost(final Post post, final Account account);

  void deleteRecommendPost(final Post post, final Account account);

  void createRecommendThread(final Thread thread, final Account account);

  void deleteRecommendThread(final Thread thread, final Account account);

  void createRecommendComment(final Comment comment, final Account account);

  void deleteRecommendComment(final Comment comment, final Account account);

  long getPostRecommendCount(final Post post);

  MyPage<RecommendCommentResponseDto> getRecommendCommentsByAccount(Long accountId, Pageable pageable);
}
