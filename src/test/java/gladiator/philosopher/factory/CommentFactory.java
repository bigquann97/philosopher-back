package gladiator.philosopher.factory;

import gladiator.philosopher.comment.entity.Comment;

public class CommentFactory {

  public static Comment createComment1() {
    return Comment.builder()
        .account(AccountFactory.createUserAccount1())
        .thread(ThreadFactory.createThread1())
        .content("댓글 내용")
        .opinion("댓글 의견")
        .build();
  }

  public static Comment createComment2() {
    return Comment.builder()
        .account(AccountFactory.createUserAccount2())
        .thread(ThreadFactory.createThread2())
        .content("댓글 내용2")
        .opinion("댓글 의견2")
        .build();
  }

  public static Comment createBlindedComment() {
    Comment comment = createComment1();
    comment.blind();
    return comment;
  }

}
