package gladiator.philosopher.comment.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.thread.entity.Thread;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  }

}