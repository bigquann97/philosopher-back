package gladiator.philosopher.account.dto;

import lombok.Getter;

@Getter
public class RecommendCommentResponseDto {

  private Long id;
  private String content;

  public RecommendCommentResponseDto(Long id, String content) {
    this.id = id;
    this.content = content;
  }
}
