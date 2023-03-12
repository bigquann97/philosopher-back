package gladiator.philosopher.rank.dto;

import gladiator.philosopher.rank.entity.Philosopher;
import lombok.Getter;

@Getter
public class UserRankingResponseDto {

  private String nickname;
  private Long count;
  private Philosopher philosopher;

  public UserRankingResponseDto( String nickname, Long count) {
    this.nickname = nickname;
    this.count = count;
  }
  public void setPhilosopher(Philosopher philosopher){
    this.philosopher = philosopher;
  }

}
