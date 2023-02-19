package gladiator.philosopher.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostModifyRequestDto {

  private String title;
  private String content;
  private Long categoryId;

  public PostModifyRequestDto(String title, String content,Long CategoryId) {
    this.title = title;
    this.content = content;
    this.categoryId = getCategoryId();
  }
}
