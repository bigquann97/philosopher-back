package gladiator.philosopher.report.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.thread.entity.Thread;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_account_id", nullable = false)
  private Account reportAccount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_account_id", nullable = false)
  private Account reportedAccount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReportCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_id")
  private Thread thread;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "comment_id")
//  private Comment comment;

  @Builder
  public Report(Account reportAccount, Account reportedAccount, ReportCategory category, Post post,
      Thread thread) {
    this.reportAccount = reportAccount;
    this.reportedAccount = reportedAccount;
    this.category = category;
    this.post = post;
    this.thread = thread;
  }

}
