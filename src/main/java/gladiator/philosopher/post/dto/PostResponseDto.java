package gladiator.philosopher.post.dto;

import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PostResponseDto {

  private final String title;
  private final String content;
  private final Long recommend;
  private final LocalDateTime createDate;
  private final LocalDateTime modifiedDate;
  private final String nickname;
  private final List<String> images;
  private final List<String> opinions;
  private final String category;

  public PostResponseDto(Post post, Long recommendCount, List<String> urls) {
    this.title = post.getTitle();
    this.content = post.getContent();
    this.recommend = recommendCount;
    this.createDate = post.getCreatedDate();
    this.modifiedDate = post.getModifiedDate();
    this.nickname = post.getAccount().getNickname();
    this.images = urls;
    this.opinions = post.getOpinions().stream().map(PostOpinion::getOpinion).collect(Collectors.toList());
    this.category = post.getCategory().getName();
  }

}
