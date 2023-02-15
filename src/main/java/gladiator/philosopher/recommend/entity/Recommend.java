package gladiator.philosopher.recommend.entity;


import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.thread.entity.Thread;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Getter
@Entity
@NoArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "recommendConstraint",
            columnNames = {"account_id", "post_id", "thread_id", "comment_id"}
        )
    }
)
public class Recommend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recommend_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "comment_id")
  private Comment comment;

  public Recommend(Account account, Post post) {
    this.post = post;
    this.account = account;
  }

  public Recommend(Thread thread, Account account) {
    this.thread = thread;
    this.account = account;
  }

  public Recommend(Comment comment, Account account) {
    this.comment = comment;
    this.account = account;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Recommend recommend = (Recommend) o;
    return id != null && Objects.equals(id, recommend.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
