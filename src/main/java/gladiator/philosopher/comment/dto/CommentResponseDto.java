package gladiator.philosopher.comment.dto;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.CommentStatus;
import gladiator.philosopher.comment.entity.Mention;
import gladiator.philosopher.common.util.TimeAdapter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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

  private String createDate;

  private String status;

  private Long recommendCount;

  private List<Long> mentioningCommentIds;

  private List<Long> mentionedCommentIds;

  public CommentResponseDto(Long commentId, String nickname, String opinion, String content,
      LocalDateTime createDate, CommentStatus status, Long recommendCount,
      Set<Mention> mentionings, Set<Mention> mentioneds) {
    this.commentId = commentId;
    this.nickname = nickname;
    this.opinion = opinion;
    this.content = content;
    this.createDate = TimeAdapter.formatToString(createDate);
    this.status = status.name();
    this.recommendCount = recommendCount;
  }

  @Builder
  public CommentResponseDto(Comment comment, Long recommendCount) {
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
    this.createDate = TimeAdapter.formatToString(comment.getCreatedDate());
    this.status = comment.getStatus().name();
    this.recommendCount = recommendCount;
  }

  public static CommentResponseDto of(Comment comment, Long recommendCount) {
    return CommentResponseDto.builder().comment(comment).recommendCount(recommendCount).build();
  }

}