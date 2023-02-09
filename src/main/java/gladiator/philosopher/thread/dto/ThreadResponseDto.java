package gladiator.philosopher.thread.dto;

import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.thread.entity.Thread;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ThreadResponseDto {

  private final Long id;
  private final String title;
  private final String content;
  private final List<String> images;
  private final Long recommend;
  private final String nickname;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;

  @Builder
  public ThreadResponseDto(final Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.images = thread.getPostImages().stream().map(PostImage::getUniqueName)
        .collect(Collectors.toList());
    this.recommend = 1L;
    this.nickname = thread.getAccount().getNickname();
    this.startDate = thread.getStartTime();
    this.endDate = thread.getEndTime();
  }
}
