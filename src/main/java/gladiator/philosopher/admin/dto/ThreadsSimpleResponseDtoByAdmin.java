package gladiator.philosopher.admin.dto;

import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ThreadsSimpleResponseDtoByAdmin {

  private final Long id;
  private final String title; // 제목
  private final String content; // 내용
  private final List<String> imageUrl; // 이미지 주소 ->
  private final Long recommendCount; // 추천 수 ->
  private final String nickname; // 작성자 명
  private final ThreadStatus status;
  private final LocalDateTime createdDate; // 시작시간
  private final LocalDateTime endDate; // 끝나는 시간

  public ThreadsSimpleResponseDtoByAdmin(Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.imageUrl = thread.getPostImages().stream().map(PostImage::getOriginalName).collect(
        Collectors.toList());
    this.recommendCount = thread.getRecommends().stream().map(Recommend::getThread).count();
    this.nickname = thread.getAccount().getNickname();
    this.status = thread.getStatus();
    this.createdDate = thread.getCreatedDate();
    this.endDate = thread.getEndDate();
  }


}
