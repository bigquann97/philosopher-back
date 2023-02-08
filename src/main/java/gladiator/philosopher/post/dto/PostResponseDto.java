package gladiator.philosopher.post.dto;

import gladiator.philosopher.post.entity.Post;
import javax.persistence.Column;
import lombok.Data;

@Data
public class PostResponseDto {

  @Column(nullable = false)
  private Long postId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String nickname;

  @Column
  private String image;

  @Column(nullable = false)
  private String content;

  public PostResponseDto(Post post) {
    this.postId = post.getId();
    this.nickname = post.getAccount().getNickname();
    this.title = post.getTitle();
    this.image = post.getImage();
    this.content = post.getContent();
  }
}
