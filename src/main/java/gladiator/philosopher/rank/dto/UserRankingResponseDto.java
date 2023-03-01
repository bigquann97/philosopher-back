package gladiator.philosopher.rank.dto;

import lombok.Getter;

@Getter
public class UserRankingResponseDto {

  private String nickname;
  private Long count;

  public UserRankingResponseDto(String nickname, Long count) {
    this.nickname = nickname;
    this.count = count;
  }
}
