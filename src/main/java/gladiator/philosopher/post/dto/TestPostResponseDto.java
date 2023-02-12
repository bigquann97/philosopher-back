package gladiator.philosopher.post.dto;

import gladiator.philosopher.account.entity.Account;
import lombok.Getter;

@Getter
public class TestPostResponseDto {

  private Long id;
  private String title;
  private String content;
  private Account account;

  public TestPostResponseDto(Long id, String title, String content, Account account) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.account = account;
  }
}
