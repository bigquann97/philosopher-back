package gladiator.philosopher.account.dto;

import gladiator.philosopher.account.entity.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SignInResponseDto {

  private final String nickname;
  private final String accessToken;

  public SignInResponseDto(String nickName, String accessToken) {
    this.nickname = nickName;
    this.accessToken = accessToken;
  }

  public static SignInResponseDto of(Account account, String accessToken) {
    return new SignInResponseDto(account.getNickname(), accessToken);
  }
}
