package gladiator.philosopher.post.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class PostRequestDto {

  private final String title;
  private final String content;
  private final List<String> opinions;

  public PostRequestDto(String title, String content, List<String> opinions) {
    this.title = title;
    this.content = content;
    this.opinions = opinions;
  }
}
