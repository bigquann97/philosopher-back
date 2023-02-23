package gladiator.philosopher.post.dto;

import gladiator.philosopher.post.enums.PostStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostResponseDtoByQueryDsl {

  // post
  private Long id;
  private String title;
  private String category;
  private LocalDateTime createdDate;
  private PostStatus status;
  // account
  private String nickname;
  //recommend
  private Long recommendCount;


  public PostResponseDtoByQueryDsl(Long id, String title, String category,
      LocalDateTime createdDate,
      PostStatus status, String nickname, Long recommendCount) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.createdDate = createdDate;
    this.status = status;
    this.nickname = nickname;
    this.recommendCount = recommendCount;
  }
}
