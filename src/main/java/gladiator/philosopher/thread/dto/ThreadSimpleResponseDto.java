package gladiator.philosopher.thread.dto;

import gladiator.philosopher.common.util.TimeAdapter;
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

  private Long id;
  private String title;
  private String category;
  private Long commentCount;
  private Long recommendCount;
  private String nickname;
  private String createdDate;
  private String endDate;

  public ThreadSimpleResponseDto(Long id, String title, String name, Long commentCount,
      Long recommendCount, String nickname, LocalDateTime createdDate, LocalDateTime endDate) {
    this.id = id;
    this.title = title;
    this.category = name;
    this.commentCount = commentCount;
    this.recommendCount = recommendCount;
    this.nickname = nickname;
    this.createdDate = TimeAdapter.formatToString(createdDate);
    this.endDate = TimeAdapter.formatToString(endDate);
  }

  @Builder
  public ThreadSimpleResponseDto(Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.category = thread.getCategory().getName();
    this.commentCount = (long) thread.getRecommends().size();
    this.recommendCount = (long) thread.getRecommends().size();
    this.nickname = thread.getAccount().getNickname();
    this.createdDate = TimeAdapter.formatToString(thread.getCreatedDate());
    this.endDate = TimeAdapter.formatToString(thread.getEndDate());
  }

  public static ThreadSimpleResponseDto of(Thread thread) {
    return ThreadSimpleResponseDto.builder().thread(thread).build();
  }

  public static List<ThreadSimpleResponseDto> of(List<Thread> threadList) {
    return threadList.stream().map(ThreadSimpleResponseDto::of).collect(Collectors.toList());
  }
}

/*
  @Builder
  public ThreadSimpleResponseDto(Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.category = thread.getCategory().getName();
    this.commentCount = (long) thread.getRecommends().size();
    this.recommendCount = (long) thread.getRecommends().size();
    this.nickname = thread.getAccount().getNickname();
    this.createdDate = TimeAdapter.formatToString(thread.getCreatedDate());
    this.endDate = TimeAdapter.formatToString(thread.getEndDate());
  }

  public static ThreadSimpleResponseDto of(Thread thread) {
    return ThreadSimpleResponseDto.builder().thread(thread).build();
  }

  public static List<ThreadSimpleResponseDto> of(List<Thread> threadList) {
    return threadList.stream().map(ThreadSimpleResponseDto::of).collect(Collectors.toList());
  }

 */