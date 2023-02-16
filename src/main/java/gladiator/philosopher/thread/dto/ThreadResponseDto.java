package gladiator.philosopher.thread.dto;

import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadImage;
import gladiator.philosopher.thread.entity.ThreadOpinion;
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
  private final String nickname;
  private final LocalDateTime createdDate;
  private final LocalDateTime endDate;
  private final Long recommend;
  private final List<String> images;
  private final List<String> opinions;

  @Builder
  public ThreadResponseDto(final Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.images = thread.getThreadImages().stream().map(ThreadImage::getImageUrl)
        .collect(Collectors.toList());
    this.recommend = (long) thread.getRecommends().size();
    this.nickname = thread.getAccount().getNickname();
    this.createdDate = thread.getCreatedDate();
    this.endDate = thread.getEndDate();
    this.opinions = thread.getOpinions().stream().map(ThreadOpinion::getOpinion)
        .collect(Collectors.toList());
  }

  public static ThreadResponseDto of(Thread thread) {
    return ThreadResponseDto.builder().thread(thread).build();
  }

  public static List<ThreadResponseDto> of(List<Thread> threads) {
    return threads.stream().map(ThreadResponseDto::of).collect(Collectors.toList());
  }

}
