package gladiator.philosopher.rank.dto;

import lombok.Getter;

@Getter
public class UserRankingResponseDto {

  private Long id;
  private String nickname;
  private Long count;

  public UserRankingResponseDto(Long id, String nickname, Long count) {
    this.id = id;
    this.nickname = nickname;
    this.count = count;
  }
}
