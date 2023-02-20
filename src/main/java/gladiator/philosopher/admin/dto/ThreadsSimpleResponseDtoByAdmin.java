package gladiator.philosopher.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadLocation;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ThreadsSimpleResponseDtoByAdmin {

  private final Long id;
  private final String title; // 제목
  private final String content; // 내용
  private final String category;
  private final Long recommendCount; // 추천 수 ->
  private final String nickname; // 작성자 명
  private final ThreadLocation location;


  public ThreadsSimpleResponseDtoByAdmin(Thread thread, Long recommendCount) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.category = thread.getCategory().getName();
    this.recommendCount = recommendCount;
    this.nickname = thread.getAccount().getNickname();
    this.location = thread.getLocation();
  }

  @QueryProjection
  public ThreadsSimpleResponseDtoByAdmin(Long id, String title, String content, String category,
      Long recommendCount, String nickname, ThreadLocation location) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.category = category;
    this.recommendCount = recommendCount;
    this.nickname = nickname;
    this.location = location;
  }
}
