package gladiator.philosopher.report.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.enums.ReportCategory;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @UniqueConstraint(name = "thrdRptCnst", columnNames = {"reporter_account_id", "thread_id"}),
})
public class ThreadReport extends Report {

  @Column(name = "thread_id")
  private Long threadId;

  @Builder
  public ThreadReport(String content, Account reporter, Account reported, ReportCategory category,
      Long threadId) {
    this.content = content;
    this.reporter = reporter;
    this.reported = reported;
    this.category = category;
    this.threadId = threadId;
  }

}