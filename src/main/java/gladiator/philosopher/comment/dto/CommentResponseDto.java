package gladiator.philosopher.comment.dto;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.util.TimeAdapter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {

  private Long commentId;

  private String nickname;

  private String opinion;

  private String content;

  private String createDate;

  private String status;

  private Long recommendCount;

  private List<MentionResponseDto> mentioningComments;

  private List<MentionResponseDto> mentionedComments;

  private String imageUrl;

  @Builder
  public CommentResponseDto(Comment comment, Long recommendCount, String imageUrl) {
    this.commentId = comment.getId();
    this.nickname = comment.getAccount().getNickname();
    this.opinion = comment.getOpinion();
    this.content = comment.getContent();
    this.mentioningComments = comment.getMentionings().stream()
        .map(x -> {
          Comment c = x.getMentionedComment();
          Long id = c.getId();
          String content = c.getContent();
          return MentionResponseDto.of(id, content);
        })
        .collect(Collectors.toList());
    this.mentionedComments = comment.getMentioneds().stream()
        .map(x -> {
          Comment c = x.getMentioningComment();
          Long id = c.getId();
          String content = c.getContent();
          return MentionResponseDto.of(id, content);
        })
        .collect(Collectors.toList());
    this.createDate = TimeAdapter.formatToString(comment.getCreatedDate());
    this.status = comment.getStatus().name();
    this.recommendCount = recommendCount;
    this.imageUrl = imageUrl;
  }

}

/*
  public CommentResponseDto(Long commentId, String nickname, String opinion, String content,
      LocalDateTime createDate, CommentStatus status, Long recommendCount,
      List<Mention> mentionings, List<Mention> mentioneds) {
    this.commentId = commentId;
    this.nickname = nickname;
    this.opinion = opinion;
    this.content = content;
    this.createDate = TimeAdapter.formatToString(createDate);
    this.status = status.name();
    this.mentioningComments = mentionings.stream().map(x -> {
      if (x != null) {
        Comment c = x.getMentionedComment();
        Long id = c.getId();
        String mentionContent = c.getContent();
        return MentionResponseDto.of(id, mentionContent);
      } else {
        return null;
      }
    }).collect(Collectors.toList());
    this.mentionedComments = mentioneds.stream()
        .map(x -> {
          if (x != null) {
            Comment c = x.getMentioningComment();
            Long id = c.getId();
            String mentionedContent = c.getContent();
            return MentionResponseDto.of(id, mentionedContent);
          } else {
            return null;
          }
        })
        .collect(Collectors.toList());
    this.recommendCount = recommendCount;
  }

  public static CommentResponseDto of(Comment comment, Long recommendCount) {
    return CommentResponseDto.builder().comment(comment).recommendCount(recommendCount).build();
  }

 */