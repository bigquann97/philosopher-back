package gladiator.philosopher.comment.dto;

import gladiator.philosopher.common.util.TimeAdapter;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class FavCommentResponseDto {

  private final Long id;
  private final String content;
  private final String nickname;
  private final String imageUrl;
  private final Long likeCount;
  private final String opinion;
  private final String createDate;

  public FavCommentResponseDto(Long id, String content, String nickname, String imageUrl,
      Long likeCount, String opinion, LocalDateTime createdDate) {
    this.id = id;
    this.content = content;
    this.nickname = nickname;
    this.imageUrl = imageUrl;
    this.likeCount = likeCount;
    this.opinion = opinion;
    this.createDate = TimeAdapter.formatToString(createdDate);
  }
}
