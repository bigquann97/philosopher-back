package gladiator.philosopher.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class PostRequestDto {
  private final String title;
  private final String content;
  public PostRequestDto(String title, String content) {
    this.title = title;
    this.content = content;
  }


}
