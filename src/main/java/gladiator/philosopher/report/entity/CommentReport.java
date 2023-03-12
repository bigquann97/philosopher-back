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
    @UniqueConstraint(name = "cmntRptCnst", columnNames = {"reporter_account_id", "comment_id"}),
})
public class CommentReport extends Report {

  @Column(name = "comment_id")
  private Long commentId;

  @Builder
  public CommentReport(String content, Account reporter, Account reported, ReportCategory category,
      Long commentId) {
    this.content = content;
    this.reporter = reporter;
    this.reported = reported;
    this.category = category;
    this.commentId = commentId;
  }

}