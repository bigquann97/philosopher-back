package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import java.util.List;

public interface RecommendService {

  void createRecommendPost(Long postId, Account account);

  void deleteRecommendPost(Long postId, Account account);

  void createRecommendThread(Long threadId, Account account);

  void deleteRecommendThread(Long threadId, Account account);

  void createRecommendComment(Long commentId, Account account);

  void deleteRecommendComment(Long commentId, Account account);

  List<Recommend> getPostRecommends(Post post);
}
