package gladiator.philosopher.recommend.entity;

import gladiator.philosopher.account.entity.Account;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Recommend {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "recommend_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "comment_id")
  private Comment commnet;

  public recommendPost(Account account, Post post) {
    this.account = account;
    this.post = post;
  }

  public recommendThread(Account account, Thread thread) {
    this.account = account;
    this.thread = thread;
  }

  public recommendCommnet(Account account, Comment commnet) {
    this.account = account;
    this.commnet = commnet;
  }

}
