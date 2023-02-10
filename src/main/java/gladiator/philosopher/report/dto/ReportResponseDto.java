package gladiator.philosopher.report.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.entity.Report;
import gladiator.philosopher.report.entity.ReportCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportResponseDto {

  private final Long id;
  private final String reportedAccount;
  private final String reportAccount;
  private final ReportCategory category;
  private final Long post;
  private final Long thread;
  private final String content;

}
