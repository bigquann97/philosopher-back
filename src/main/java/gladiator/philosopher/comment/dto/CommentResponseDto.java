package gladiator.philosopher.comment.dto;

import gladiator.philosopher.comment.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

  private Long commentId;

  private String nickname;

  private String opinion;

  private String content;

  private List<Long> mentioningCommentIds;

  private List<Long> mentionedCommentIds;

  private LocalDateTime createDate;

  private CommentStatus status;

  private Long recommendCount;

  @Builder
  public CommentResponseDto(Comment comment) {
    this.commentId = comment.getId();
    this.nickname = comment.getAccount().getNickname();
    this.opinion = comment.getOpinion();
    this.content = comment.getContent();
    this.mentioningCommentIds = comment.getMentionings().stream()
        .map(x -> x.getMentionedComment().getId()).collect(
            Collectors.toList());
    this.mentionedCommentIds = comment.getMentioneds().stream()
        .map(x -> x.getMentioningComment().getId()).collect(
            Collectors.toList());
    this.createDate = comment.getCreatedDate();
    this.status = comment.getStatus();
    this.recommendCount = (long) comment.getRecommends().size();
  }

  public static CommentResponseDto of(Comment comment) {
    return CommentResponseDto.builder().comment(comment).build();
  }

  public static List<CommentResponseDto> of(List<Comment> comments) {
    return comments.stream().map(CommentResponseDto::of).collect(Collectors.toList());
  }
}