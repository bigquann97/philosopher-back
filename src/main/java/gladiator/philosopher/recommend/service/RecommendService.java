package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;

public interface RecommendService {

  void createRecommendPost(Post post, Account account);

  void deleteRecommendPost(Post post, Account account);

  void createRecommendThread(Thread thread, Account account);

  void deleteRecommendThread(Thread thread, Account account);

  void createRecommendComment(Comment comment, Account account);

  void deleteRecommendComment(Comment comment, Account account);

  List<Recommend> getPostRecommends(Post post);
}
