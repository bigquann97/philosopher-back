package gladiator.philosopher.notification.dto;

import gladiator.philosopher.account.entity.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NotificationRequestDto {

  private String redirectUrl;
  private String content;
  private Account account;

  @Builder
  public NotificationRequestDto(String redirectUrl, String content, Account account) {
    this.redirectUrl = redirectUrl;
    this.content = content;
    this.account = account;
  }
  
}