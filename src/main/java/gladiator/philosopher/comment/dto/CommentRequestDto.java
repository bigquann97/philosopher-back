package gladiator.philosopher.comment.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.thread.entity.Thread;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

  private String content;

  public CommentRequestDto(final String content) {
    this.content = content;
  }

  public Comment toEntity(final Thread thread, final Account account) {
    return Comment.builder()
        .account(account)
        .thread(thread)
        .content(content)
        .build();
  }
}
