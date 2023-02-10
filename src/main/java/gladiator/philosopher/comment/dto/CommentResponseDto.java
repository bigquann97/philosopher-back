package gladiator.philosopher.comment.dto;

import gladiator.philosopher.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

  private Long commentId;

  private String nickname;

  private String content;

  private LocalDateTime createDate;

  private LocalDateTime modifiedDate;

  public CommentResponseDto(Comment comment) {
    this.commentId = comment.getId();
    this.nickname = comment.getAccount().getNickname();
    this.content = comment.getContent();
    this.createDate = comment.getCreatedDate();
    this.modifiedDate = comment.getModifiedDate();
  }

}