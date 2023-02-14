package gladiator.philosopher.comment.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentStatus;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.mention.entity.Mention;
import gladiator.philosopher.thread.entity.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "account_Id")
  private Account account;

  @ManyToOne
  @JoinColumn(name = "thread_Id")
  private Thread thread;

  @Column(nullable = false)
  private String content;

  @Enumerated(EnumType.STRING)
  private CommentStatus status;

  @OneToMany(mappedBy = "mentioningComment")
  private List<Mention> mentioningSet = new ArrayList<>();

  @OneToMany(mappedBy = "mentionedComment")
  private List<Mention> mentionedSet = new ArrayList<>();

  @Builder
  public Comment(Long id, Account account, Thread thread, String content) {
    this.id = id;
    this.account = account;
    this.thread = thread;
    this.content = content;
  }

  public void modifyComment(String content) {
    this.content = content;
  }

  public void blind() {
    this.status = CommentStatus.BLINDED;
  }

  public void releaseBlind() {
    this.status = CommentStatus.ACTIVE;
  }


  public boolean isWriter(Account account) {
    return this.account.equals(account);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Comment comment = (Comment) o;
    return id != null && Objects.equals(id, comment.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}