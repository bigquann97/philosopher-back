package gladiator.philosopher.thread.dto;

import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.thread.entity.Thread;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ThreadResponseDto {

  private final Long id;
  private final String title;
  private final String content;
  private final Integer recommend;
  private final LocalDateTime endDate;
  private final List<String> images;
  private final String nickname;

  @Builder
  public ThreadResponseDto(final Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.images = thread.getPostImages().stream().map(PostImage::getUniqueName)
        .collect(Collectors.toList());
    this.recommend = 1;
    this.nickname = thread.getAccount().getNickname();
    this.endDate = thread.getEndTime();
  }

  public static ThreadResponseDto of(Thread thread) {
    return new ThreadResponseDto(thread);
  }

  public static List<ThreadResponseDto> of(List<Thread> threads) {
    return threads.stream().map(ThreadResponseDto::of).collect(Collectors.toList());
  }
}
