package gladiator.philosopher.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequestDto {

  private String accessToken;
  private String refreshToken;

  public boolean validateToken(String validRefreshToken) {
    return this.refreshToken.equals(validRefreshToken);
  }
}