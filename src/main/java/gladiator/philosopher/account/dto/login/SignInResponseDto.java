package gladiator.philosopher.account.dto.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SignInResponseDto {

  private final String nickname;
  private final String accessToken;
  private final String refreshToken;

  public SignInResponseDto(String nickname, String accessToken, String refreshToken) {
    this.nickname = nickname;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static SignInResponseDto of(String nickname, String accessToken, String refreshToken) {
    return new SignInResponseDto(nickname, accessToken, refreshToken);
  }
}
