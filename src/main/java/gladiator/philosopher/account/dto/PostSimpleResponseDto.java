package gladiator.philosopher.account.dto;

import lombok.Getter;

@Getter
public class PostSimpleResponseDto {

  private Long id;
  private String title;
  private String content;
  private String category;

  public PostSimpleResponseDto(Long id, String title, String content, String category) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.category = category;
  }


}
