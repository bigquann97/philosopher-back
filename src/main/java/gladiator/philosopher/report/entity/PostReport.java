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
    @UniqueConstraint(name = "pstRptCnst", columnNames = {"reporter_account_id", "post_id"}),
})
public class PostReport extends Report {

  @Column(name = "post_id")
  private Long postId;

  @Builder
  public PostReport(String content, Account reporter, Account reported, ReportCategory category,
      Long postId) {
    this.content = content;
    this.reporter = reporter;
    this.reported = reported;
    this.category = category;
    this.postId = postId;
  }

}
