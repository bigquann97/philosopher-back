package gladiator.philosopher.post.dto;

import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostResponseDto {

  private final Long postId;

  private final String title;

  private final String nickname;

  private final List<String> images;

  private final int recommendCount;

  private final List<String> opinions;

  private final String content;

  private final LocalDateTime createDate;

  private final LocalDateTime modifiedDate;

  public PostResponseDto(Post post, int recommendCount, List<String> postImage) {
    this.postId = post.getId();
    this.nickname = post.getAccount().getNickname();
    this.title = post.getTitle();
    this.images = postImage;
    this.content = post.getContent();
    this.createDate = post.getCreatedDate();
    this.modifiedDate = post.getModifiedDate();
    this.recommendCount = recommendCount;
    this.opinions = post.getOpinions().stream().map(PostOpinion::getOpinion)
        .collect(Collectors.toList());
  }
}
