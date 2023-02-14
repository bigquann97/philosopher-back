package gladiator.philosopher.report.entity;

import gladiator.philosopher.account.entity.Account;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "reportConstraint",
            columnNames = {"postId", "threadId", "commentId", "report_account_id"}
        )
    }
)
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id", nullable = false)
  private Long id;

  private String content;

  private Long postId;

  private Long threadId;

  private Long commentId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReportCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_account_id", nullable = false)
  private Account reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_account_id", nullable = false)
  private Account reported;

  @Builder
  public Report(Account reporter, Account reported, ReportCategory category, Long postId,
      Long commentId, Long threadId, String content) {
    this.reporter = reporter;
    this.reported = reported;
    this.category = category;
    this.content = content;
    this.postId = postId;
    this.commentId = commentId;
    this.threadId = threadId;
  }

}
