package gladiator.philosopher.comment.dto;

import lombok.Getter;

@Getter
public class FavCommentResponseDto {

  private final Long id;
  private final String content;
  private final String nickname;
  private final String imageUrl;
  private final Long likeCount;

  public FavCommentResponseDto(Long id, String content, String nickname, String imageUrl,
      Long likeCount) {
    this.id = id;
    this.content = content;
    this.nickname = nickname;
    this.imageUrl = imageUrl;
    this.likeCount = likeCount;
  }
}
