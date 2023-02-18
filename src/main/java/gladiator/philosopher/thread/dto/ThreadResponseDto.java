package gladiator.philosopher.thread.dto;

import gladiator.philosopher.common.util.TimeAdapter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ThreadResponseDto {

  private Long id;
  private String title;
  private String content;
  private String nickname;
  private String createdDate;
  private String endDate;
  private String category;
  private Long commentCount;
  private Long recommendCount;
  private List<String> images;
  private List<String> opinions;

  public ThreadResponseDto(Long id, String title, String content, String nickname,
      LocalDateTime createdDate, LocalDateTime endDate, String name,
      Long commentCount, Long recommendCount) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.nickname = nickname;
    this.createdDate = TimeAdapter.formatToString(createdDate);
    this.endDate = TimeAdapter.formatToString(endDate);
    this.category = name;
    this.commentCount = commentCount;
    this.recommendCount = recommendCount;
  }

  public void addImages(List<String> images) {
    this.images = images;
  }

  public void addOpinions(List<String> opinions) {
    this.opinions = opinions;
  }
}

/*
  @Builder
  public ThreadResponseDto(final Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.images = thread.getThreadImages().stream().map(ThreadImage::getImageUrl)
        .collect(Collectors.toList());
    this.commentCount = (long) thread.getRecommends().size();
    this.recommendCount = (long) thread.getRecommends().size();
    this.nickname = thread.getAccount().getNickname();
    this.category = thread.getCategory().getName();
    this.createdDate = TimeAdapter.formatToString(thread.getCreatedDate());
    this.endDate = TimeAdapter.formatToString(thread.getEndDate());
    this.opinions = thread.getOpinions().stream().map(ThreadOpinion::getOpinion)
        .collect(Collectors.toList());
  }

  public static ThreadResponseDto of(Thread thread) {
    return ThreadResponseDto.builder().thread(thread).build();
  }

  public static List<ThreadResponseDto> of(List<Thread> threads) {
    return threads.stream().map(ThreadResponseDto::of).collect(Collectors.toList());
  }

 */