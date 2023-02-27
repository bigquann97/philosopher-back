package gladiator.philosopher.comment.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.enums.CommentStatus;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.thread.entity.Thread;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  private String opinion;

  @Enumerated(EnumType.STRING)
  private CommentStatus status;

  @OneToMany(mappedBy = "mentioningComment", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Mention> mentionings = new HashSet<>();

  @OneToMany(mappedBy = "mentionedComment", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Mention> mentioneds = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_Id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_Id")
  private Thread thread;

  @Builder
  public Comment(Account account, Thread thread, String content, String opinion) {
    this.account = account;
    this.thread = thread;
    this.content = content;
    this.status = CommentStatus.ACTIVE;
    this.opinion = opinion;
  }

  public void modifyComment(String content, String opinion) {
    this.opinion = opinion;
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

  public void chaneStatusToDeleted() {
    this.status = CommentStatus.DELETED;
  }

  public boolean isBlinded() {
    return this.status == CommentStatus.BLINDED;
  }

  public boolean isActive() {
    return this.status == CommentStatus.ACTIVE;
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

  public Comment modifyStatusByAdmin(CommentStatus commentStatus) {
    this.status = commentStatus;
    return this;
  }
}