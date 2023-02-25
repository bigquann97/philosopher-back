package gladiator.philosopher.post.dto;

import gladiator.philosopher.common.util.TimeAdapter;
import gladiator.philosopher.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class PostResponseDto {

  private final String title;
  private final String content;
  private final Long recommendCount;
  private final String createDate;
  private final String modifiedDate;
  private final String nickname;
  private final List<String> images;
  private final List<String> opinions;
  private final String category;

  public PostResponseDto(Post post, Long recommendCount, List<String> urls, List<String> options) {
    this.title = post.getTitle();
    this.content = post.getContent();
    this.recommendCount = recommendCount;
    this.createDate = TimeAdapter.formatToString(post.getCreatedDate());
    this.modifiedDate = TimeAdapter.formatToString(post.getModifiedDate());
    this.nickname = post.getAccount().getNickname();
    this.images = urls;
    this.opinions = options;
    this.category = post.getCategory().getName();
  }

}
