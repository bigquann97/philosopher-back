package gladiator.philosopher.thread.dto;

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
  private final LocalDateTime createdDate;
  private final LocalDateTime endDate;
  private final Long recommend;
  private List<String> images;
  private final String nickname;

  public ThreadResponseDto(Long id, String title, String content, LocalDateTime createdDate,
      LocalDateTime endDate, Long recommend, List<String> images, String nickname) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createdDate = createdDate;
    this.endDate = endDate;
    this.recommend = recommend;
    this.images = images;
    this.nickname = nickname;
  }

  @Builder
  public ThreadResponseDto(final Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.images = thread.getThreadImages().stream().map(x -> x.getImageUrl())
        .collect(Collectors.toList());
    this.recommend = 1L;
    this.nickname = thread.getAccount().getNickname();
    this.endDate = thread.getEndDate();
    this.createdDate = thread.getCreatedDate();
  }

  public static ThreadResponseDto of(Thread thread) {
    return new ThreadResponseDto(thread);
  }

  public static List<ThreadResponseDto> of(List<Thread> threads) {
    return threads.stream().map(ThreadResponseDto::of).collect(Collectors.toList());
  }

  public void addImage(List<String> images) {
    this.images = images;
  }

}
