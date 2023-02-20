package gladiator.philosopher.factory;

import gladiator.philosopher.recommend.entity.CommentRecommend;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.recommend.entity.ThreadRecommend;

public class RecommendFactory {

  public static PostRecommend createPostRecommend1() {
    return PostRecommend.builder()
        .account(AccountFactory.createUserAccount1())
        .post(PostFactory.createPost1())
        .build();
  }

  public static PostRecommend createPostRecommend2() {
    return PostRecommend.builder()
        .account(AccountFactory.createUserAccount2())
        .post(PostFactory.createPost1())
        .build();
  }

  public static ThreadRecommend createThreadRecommend1() {
    return ThreadRecommend.builder()
        .thread(ThreadFactory.createThread1())
        .account(AccountFactory.createUserAccount1())
        .build();
  }

  public static ThreadRecommend createThreadRecommend2() {
    return ThreadRecommend.builder()
        .thread(ThreadFactory.createThread1())
        .account(AccountFactory.createUserAccount2())
        .build();
  }

  public static CommentRecommend createCommentRecommend1() {
    return CommentRecommend.builder()
        .account(AccountFactory.createUserAccount1())
        .comment(CommentFactory.createComment1())
        .build();
  }

  public static CommentRecommend createCommentRecommend2() {
    return CommentRecommend.builder()
        .account(AccountFactory.createUserAccount2())
        .comment(CommentFactory.createComment1())
        .build();
  }

}
