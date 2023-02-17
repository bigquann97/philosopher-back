package gladiator.philosopher.recommend.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.thread.entity.Thread;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "trdRcmndCnst", columnNames = {"account_id", "thread_id"}),
})
public class ThreadRecommend extends Recommend {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  public ThreadRecommend(Account account, Thread thread) {
    this.account = account;
    this.thread = thread;
  }

}
