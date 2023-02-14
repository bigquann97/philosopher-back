package gladiator.philosopher.recommend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendResponseDto {

  private String message;

  public RecommendResponseDto(final String message) {
    this.message = message;
  }

}
