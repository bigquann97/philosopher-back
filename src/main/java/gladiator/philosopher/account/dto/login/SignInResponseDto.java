package gladiator.philosopher.account.dto.login;

import gladiator.philosopher.account.enums.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SignInResponseDto {

  private final String nickname;
  private final UserRole userRole;
  private final String accessToken;
  private final String refreshToken;

  public SignInResponseDto(String nickname, String accessToken, String refreshToken,
      UserRole userRole) {
    this.nickname = nickname;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.userRole = userRole;
  }

  public static SignInResponseDto of(String nickname, String accessToken, String refreshToken,
      UserRole userRole) {
    return new SignInResponseDto(nickname, accessToken, refreshToken, userRole);
  }
}
