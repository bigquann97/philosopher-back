package gladiator.philosopher.recommend.service;

import gladiator.philosopher.account.entity.Account;

public interface RecommendService {

  void modifyRecommendPost(Long postId, Account account);

/*
  void modifyRecommendThread(Long threadId, Account account);

  void modifyRecommendComment(Long commentId, Account account);
*/

}
