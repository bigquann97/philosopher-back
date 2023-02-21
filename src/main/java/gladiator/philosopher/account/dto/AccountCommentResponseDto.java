package gladiator.philosopher.account.dto;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.util.TimeAdapter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AccountCommentResponseDto {

  private Long commentId;

  private String opinion;

  private String content;

  private String createDate;

  private String status;

  private List<Long> mentioningCommentIds;

  private List<Long> mentionedCommentIds;

  @Builder
  public AccountCommentResponseDto(Comment comment) {
    this.commentId = comment.getId();
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
  }
}
