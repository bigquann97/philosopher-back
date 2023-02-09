package gladiator.philosopher.thread.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.BaseEntity;
import gladiator.philosopher.post.entity.Post;
import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thread extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false, unique = true)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ThreadStatus status;

  private LocalDateTime endTime;

  @Builder
  public Thread(Post post, Account account, LocalDateTime endTime) {
    this.post = post;
    this.account = account;
    this.status = ThreadStatus.CONTINUE;
    this.endTime = endTime;
  }

}
