package gladiator.philosopher.thread.dto;

import gladiator.philosopher.thread.entity.Thread;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ThreadSimpleResponseDto {

  private final Long id;
  private final String title;
  private final Long recommend;
  private final String nickname;
  private final LocalDateTime createdDate;
  private final LocalDateTime endDate;

  @Builder
  public ThreadSimpleResponseDto(Thread thread, Integer recommendCount) {
    this.id = thread.getId();
    this.title = thread.getTitle();
<<<<<<< HEAD
    this.recommend = recommendCount;
=======
    this.recommend = 1L;
>>>>>>> f2d8a8198dc422e08907684d1361c21319ed760d
    this.nickname = thread.getAccount().getNickname();
    this.createdDate = thread.getCreatedDate();
    this.endDate = thread.getEndDate();
  }


}
