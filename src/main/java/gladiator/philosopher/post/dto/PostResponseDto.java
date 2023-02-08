package gladiator.philosopher.post.dto;

import gladiator.philosopher.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostResponseDto {

  private final Long postId;

  private final String title;

  private final String nickname;

  private final String image;

  private final String content;

  private final LocalDateTime createDate;

  private final LocalDateTime modifiedDate;

  public PostResponseDto(Post post) {
    this.postId = post.getId();
    this.nickname = post.getAccount().getNickname();
    this.title = post.getTitle();
    this.image = post.getImage();
    this.content = post.getContent();
    this.createDate = post.getCreateDate();
    this.modifiedDate = post.getModDate();
  }
}
