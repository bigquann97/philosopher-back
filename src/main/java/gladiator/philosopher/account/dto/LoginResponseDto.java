package gladiator.philosopher.account.dto;

import gladiator.philosopher.account.entity.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LoginResponseDto {

  private final String nickName;
  private final String accessToken;

  public LoginResponseDto(String nickName, String accessToken) {
    this.nickName = nickName;
    this.accessToken = accessToken;
  }

  public static LoginResponseDto of(Account account, String accessToken) {
    return new LoginResponseDto(account.getNickName(), accessToken);
  }
}
