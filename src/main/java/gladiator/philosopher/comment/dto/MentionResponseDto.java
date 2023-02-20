package gladiator.philosopher.comment.dto;

import lombok.Getter;

@Getter
public class MentionResponseDto {

  private Long id;

  private String content;

  public MentionResponseDto(Long id, String content) {
    this.id = id;
    this.content = content;
  }

  public static MentionResponseDto of(Long id, String content) {
    return new MentionResponseDto(id, content);
  }

}
