package gladiator.philosopher.comment.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.thread.entity.Thread;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class CommentRequestDto {

  private String opinion;
  private String content;

  public Comment toEntity(final Thread thread, final Account account) {
    return Comment.builder()
        .account(account)
        .thread(thread)
        .content(this.content)
        .opinion(this.opinion)
        .build();
  }
  
}
