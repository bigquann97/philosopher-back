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
public class ThreadSimpleResponseDto {

  private final Long id;
  private final String title;
  private final Long recommend;
  private final String nickname;
  private final LocalDateTime createdDate;
  private final LocalDateTime endDate;

  @Builder
  public ThreadSimpleResponseDto(Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.recommend = (long) thread.getRecommends().size();
    this.nickname = thread.getAccount().getNickname();
    this.createdDate = thread.getCreatedDate();
    this.endDate = thread.getEndDate();
  }

  public static ThreadSimpleResponseDto of(Thread thread) {
    return ThreadSimpleResponseDto.builder().thread(thread).build();
  }

  public static List<ThreadSimpleResponseDto> of(List<Thread> threadList) {
    return threadList.stream().map(ThreadSimpleResponseDto::of).collect(Collectors.toList());
  }

}
