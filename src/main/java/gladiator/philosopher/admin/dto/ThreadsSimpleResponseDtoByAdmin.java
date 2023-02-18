package gladiator.philosopher.admin.dto;

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
  private final List<String> imageUrl; // 이미지 주소 ->
  private final Long recommendCount; // 추천 수 ->
  private final String nickname; // 작성자 명
  private final ThreadLocation location;
  private final LocalDateTime createdDate; // 시작시간
  private final LocalDateTime endDate; // 끝나는 시간

  public ThreadsSimpleResponseDtoByAdmin(Thread thread) {
    this.id = thread.getId();
    this.title = thread.getTitle();
    this.content = thread.getContent();
    this.imageUrl = null; // threadImage 연관관계 끊어내서 null 처리
//        thread.getThreadImages().stream().map(ThreadImage::getImageUrl)
//        .collect(Collectors.toList());
    this.recommendCount = null; //threadRecommends 연관과계 끊어내서 null 처리
//        thread.getRecommends().stream().map(ThreadRecommend::getThread).count();
    this.nickname = thread.getAccount().getNickname();
    this.location = thread.getLocation();
    this.createdDate = thread.getCreatedDate();
    this.endDate = thread.getEndDate();
  }


}
