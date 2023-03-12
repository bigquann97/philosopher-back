package gladiator.philosopher.recommend.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "pstRcmndCnst", columnNames = {"account_id", "post_id"}),
})
public class PostRecommend extends Recommend {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Builder
  public PostRecommend(Account account, Post post) {
    this.account = account;
    this.post = post;
  }

}
