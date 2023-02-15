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
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class PostResponseDto {

  // post
  private final Long id;
  private final String title;
  private final Long recommend;
  private final String content;
  private final LocalDateTime createDate;
  private final LocalDateTime modifiedDate;

  // account
  private final String nickname;

  // image ( 양방향 )
  private final List<String> images;

  // opinions ( 양방향 )
  private final List<String> opinions;

  // category ( 단방향 )
  private final Category category;


  public PostResponseDto(Post post, long recommendCount) {
    this.id = post.getId();
    this.nickname = post.getAccount().getNickname();
    this.title = post.getTitle();
    this.images = post.getImages().stream().map(PostImage::getImageUrl)
        .collect(Collectors.toList());
    this.content = post.getContent();
    this.createDate = post.getCreatedDate();
    this.modifiedDate = post.getModifiedDate();
    this.recommend = recommendCount;
    this.opinions = post.getOpinions().stream().map(PostOpinion::getOpinion)
        .collect(Collectors.toList());
    this.category  = post.getCategory();
  }

  public PostResponseDto(Long id, String title, Long recommend, String content,
      LocalDateTime createDate, LocalDateTime modifiedDate, String nickname, List<String> images,
      List<String> opinions, Category category) {
    this.id = id;
    this.title = title;
    this.recommend = recommend;
    this.content = content;
    this.createDate = createDate;
    this.modifiedDate = modifiedDate;
    this.nickname = nickname;
    this.images = images;
    this.opinions = opinions;
    this.category = category;
  }
}
