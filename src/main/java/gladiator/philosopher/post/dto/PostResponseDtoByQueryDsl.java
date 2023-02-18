package gladiator.philosopher.post.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
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
  private Long recommend; // 추천수


  public PostResponseDtoByQueryDsl(Long id, String title, String category, LocalDateTime createdDate,
      PostStatus status, String nickname, Long recommend) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.createdDate = createdDate;
    this.status = status;
    this.nickname = nickname;
    this.recommend = recommend;
  }
}
