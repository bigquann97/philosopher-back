package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import java.util.List;

public interface RecommendService {

  void modifyRecommendPost(Long postId, Account account);

/*
  void modifyRecommendThread(Long threadId, Account account);

  void modifyRecommendComment(Long commentId, Account account);
*/

  List<Recommend> getPostRecommends(Post post);
}
