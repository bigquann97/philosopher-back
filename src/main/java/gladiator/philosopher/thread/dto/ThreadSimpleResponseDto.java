package gladiator.philosopher.thread.dto;

import gladiator.philosopher.thread.entity.Thread;
import java.time.LocalDateTime;
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

  public ThreadSimpleResponseDto(Long id, String title, Long recommend, String nickname,
      LocalDateTime createdDate, LocalDateTime endDate) {
    this.id = id;
    this.title = title;
    this.recommend = recommend;
    this.nickname = nickname;
    this.createdDate = createdDate;
    this.endDate = endDate;
  }

  @Builder
  public ThreadSimpleResponseDto(Thread thread, Long recommendCount) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.recommend = recommendCount;
    this.nickname = thread.getAccount().getNickname();
    this.createdDate = thread.getCreatedDate();
    this.endDate = thread.getEndDate();
  }

}
