package gladiator.philosopher.mention.entity;

import gladiator.philosopher.comment.entity.Comment;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mention {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 멘션 한 코멘트
  @ManyToOne
  @JoinColumn(name = "mentioning_comment_id")
  private Comment mentioningComment;

  // 멘션 당한 코멘트
  @ManyToOne
  @JoinColumn(name = "mentioned_comment_id")
  private Comment mentionedComment;

  @Builder
  public Mention(Comment mentioningComment, Comment mentionedComment) {
    this.mentioningComment = mentioningComment;
    this.mentionedComment = mentionedComment;
  }

}
