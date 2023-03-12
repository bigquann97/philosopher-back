package gladiator.philosopher.comment.dto;

import lombok.Getter;

@Getter
public class CommentOpinionStatsDto {

  private final String opinion;
  private final Long ratio;

  public CommentOpinionStatsDto(String opinion, Long ratio) {
    this.opinion = opinion;
    this.ratio = ratio;
  }

}
