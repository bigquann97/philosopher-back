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

  private int recommendCount;

  @Builder
  public CommentResponseDto(Long commentId, String nickname, String opinion, String content,
      LocalDateTime createDate, CommentStatus status, int recommendCount,
      List<Long> mentioningCommentIds, List<Long> mentionedCommentIds) {
    this.commentId = commentId;
    this.nickname = nickname;
    this.opinion = opinion;
    this.content = content;
    this.createDate = createDate;
    this.status = status;
    this.mentioningCommentIds = mentioningCommentIds;
    this.mentionedCommentIds = mentionedCommentIds;
    this.recommendCount = recommendCount;
  }

  public CommentResponseDto(final Comment comment) {
    this.commentId = comment.getId();
    this.nickname = comment.getAccount().getNickname();
    this.content = comment.getContent();
    this.createDate = comment.getCreatedDate();
    this.opinion = comment.getOpinion();
  }

  public static CommentResponseDto of(Comment comment) {
    return CommentResponseDto.builder()
        .commentId(comment.getId())
        .nickname(comment.getAccount().getNickname())
        .opinion(comment.getOpinion())
        .content(comment.getContent())
        .mentioningCommentIds(
            comment.getMentionings().stream().map(x -> x.getMentionedComment().getId()).collect(
                Collectors.toList()))
        .mentionedCommentIds(
            comment.getMentioneds().stream().map(x -> x.getMentioningComment().getId()).collect(
                Collectors.toList()))
        .createDate(comment.getCreatedDate())
        .status(comment.getStatus())
        .recommendCount(comment.getRecommends().size())
        .build();
  }

  public static List<CommentResponseDto> of(List<Comment> comments) {
    return comments.stream().map(CommentResponseDto::of).collect(Collectors.toList());
  }
}