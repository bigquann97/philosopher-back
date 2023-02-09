package gladiator.philosopher.post.dto;

import gladiator.philosopher.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostsResponseDto {

  private final Long id;
  private final String title;
  private final LocalDateTime createDate;

  public PostsResponseDto(Post post) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.createDate = post.getCreateDate();
  }
}
