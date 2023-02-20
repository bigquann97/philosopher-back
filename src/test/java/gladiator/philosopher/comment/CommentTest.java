package gladiator.philosopher.comment;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.CommentStatus;
import gladiator.philosopher.factory.AccountFactory;
import gladiator.philosopher.factory.CommentFactory;
import gladiator.philosopher.factory.ThreadFactory;
import gladiator.philosopher.thread.entity.Thread;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentTest {

  @DisplayName("1. builder")
  @Test
  void test_1() {
    // given
    Account account = AccountFactory.createUserAccount1();
    Thread thread = ThreadFactory.createThread1();
    String content = "content";
    String opinion = "의견1";

    // when
    Comment comment = Comment.builder()
        .account(account)
        .thread(thread)
        .content(content)
        .opinion(opinion)
        .build();

    // then
    Assertions.assertThat(comment.getAccount()).isEqualTo(account);
    Assertions.assertThat(comment.getThread()).isEqualTo(thread);
    Assertions.assertThat(comment.getContent()).isEqualTo(content);
    Assertions.assertThat(comment.getOpinion()).isEqualTo(opinion);
  }

  @DisplayName("2. modify Comment")
  @Test
  void test_2() {
    // given
    Comment comment = CommentFactory.createComment1();
    String afterContent = "새로운 내용";
    String afterOpinion = "새로운 의견";

    // when
    comment.modifyComment(afterContent, afterOpinion);

    // then
    Assertions.assertThat(comment.getContent()).isEqualTo(afterContent);
    Assertions.assertThat(comment.getOpinion()).isEqualTo(afterOpinion);
  }

  @DisplayName("3. blind Comment")
  @Test
  void test_3() {
    // given
    Comment comment = CommentFactory.createComment1();

    // when
    comment.blind();

    // then
    Assertions.assertThat(comment.isBlinded()).isTrue();
    Assertions.assertThat(comment.getStatus()).isNotEqualTo(CommentStatus.ACTIVE);
  }

  @DisplayName("4. release blind test")
  @Test
  void test_4() {
    // given
    Comment comment = CommentFactory.createBlindedComment();

    // when
    comment.releaseBlind();

    // then
    Assertions.assertThat(comment.isBlinded()).isFalse();
    Assertions.assertThat(comment.getStatus()).isEqualTo(CommentStatus.ACTIVE);
  }

  @DisplayName("5. isWriter Test")
  @Test
  void test_5() {
    Account account = AccountFactory.createUserAccount1();
    Comment comment = CommentFactory.createComment2();

    // when, then
    Assertions.assertThat(comment.isWriter(account)).isFalse();
    Assertions.assertThat(comment.isWriter(comment.getAccount())).isTrue();
  }

}
