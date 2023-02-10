package gladiator.philosopher.report.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
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
  private Account reporter; // 널이면 안되고

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_account_id", nullable = false)
  private Account reported; // 이친구도 널이면 안되고

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReportCategory category; // 널이면 안되고

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post; // 널이어도 되고

  private String content;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "comment_id")
  private Comment comment;

  @Builder(builderMethodName = "postReport")
  public Report(Account reporter, Account reported, ReportCategory category,
      Post post, String content) {
    this.reporter = reporter;
    this.reported = reported;
    this.category = category;
    this.content = content;
    this.post = post;
  }

  @Builder(builderMethodName = "commentReport")
  public Report(Account reporter, Account reported, ReportCategory category,
      Comment comment, String content) {
    this.reporter = reporter;
    this.reported = reported;
    this.category = category;
    this.comment = comment;
    this.content = content;
  }

  @Builder(builderMethodName = "threadReport")
  public Report(Account reporter, Account reported, ReportCategory category,
      Thread thread, String content) {
    this.reporter = reporter;
    this.reported = reported;
    this.category = category;
    this.thread = thread;
    this.content = content;
  }

}
