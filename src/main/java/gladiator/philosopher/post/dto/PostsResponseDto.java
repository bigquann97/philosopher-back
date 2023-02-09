package gladiator.philosopher.post.dto;

import gladiator.philosopher.post.entity.Post;
import java.time.LocalDateTime;

public class PostsResponseDto {

  private final String title;
  private final LocalDateTime modifiedDate;

  public PostsResponseDto(Post post) {
    this.title = post.getTitle();
    this.modifiedDate = post.getModDate();
  }
}
