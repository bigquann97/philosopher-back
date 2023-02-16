package gladiator.philosopher.report.entity;

import gladiator.philosopher.Account.entity.Account;
import java.util.Objects;
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
import org.hibernate.Hibernate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "pstCnst", columnNames = {"reporter_account_id", "postId"}),
    @UniqueConstraint(name = "thdCnst", columnNames = {"reporter_account_id", "threadId"}),
    @UniqueConstraint(name = "cmtCnst", columnNames = {"reporter_account_id", "commentId"})
})
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
  @JoinColumn(name = "reporter_account_id", nullable = false)
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Report report = (Report) o;
    return id != null && Objects.equals(id, report.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
