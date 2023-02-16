package gladiator.philosopher.account.dto.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SignInResponseDto {

  private final String email;
  private final String accessToken;
  private final String refreshToken;

  public SignInResponseDto(String email, String accessToken, String refreshToken) {
    this.email = email;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static SignInResponseDto of(String email, String accessToken, String refreshToken) {
    return new SignInResponseDto(email, accessToken, refreshToken);
  }
}
