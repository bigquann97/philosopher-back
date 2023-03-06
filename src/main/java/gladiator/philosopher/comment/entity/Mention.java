package gladiator.philosopher.comment.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint
        (name = "mentionCnst", columnNames = {"mentioning_comment_id", "mentioned_comment_id"})
})
public class Mention {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentioning_comment_id")
  private Comment mentioningComment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentioned_comment_id")
  private Comment mentionedComment;

  @Builder
  public Mention(final Comment mentioningComment, final Comment mentionedComment) {
    this.mentioningComment = mentioningComment;
    this.mentionedComment = mentionedComment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Mention mention = (Mention) o;
    return id != null && Objects.equals(id, mention.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
