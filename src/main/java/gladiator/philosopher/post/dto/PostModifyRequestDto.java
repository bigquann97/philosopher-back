package gladiator.philosopher.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
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
