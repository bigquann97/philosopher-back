package gladiator.philosopher.account.dto;

import lombok.Getter;

@Getter
public class ReissueResponseDto {

  private final String accessToken;
  private final String refreshToken;

  public ReissueResponseDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static ReissueResponseDto of(String accessToken, String refreshToken) {
    return new ReissueResponseDto(accessToken, refreshToken);
  }

}
