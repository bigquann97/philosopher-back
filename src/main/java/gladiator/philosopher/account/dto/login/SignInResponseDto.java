package gladiator.philosopher.account.dto.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SignInResponseDto {

  private final String accessToken;
  private final String refreshToken;

  public SignInResponseDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static SignInResponseDto of(String accessToken, String refreshToken) {
    return new SignInResponseDto(accessToken, refreshToken);
  }
}
