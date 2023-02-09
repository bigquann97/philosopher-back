package gladiator.philosopher.report.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.entity.Report;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportResponseDto {

  private final String reportedAccount;
  private final String reportAccount;
  private final String category;
  private final Long post;
  private final Long thread;

//  public ReportResponseDto(String reportedAccount, String category, Long post, Long thread, String reportAccount) {
//    this.reportedAccount = reportedAccount;
//    this.category = category;
//    this.post = post;
//    this.thread = thread;
//    this.reportAccount = reportAccount;
//  }
//
//  public static ReportResponseDto of(Account account, ReportResponseDto reportResponseDto){
//    return new ReportResponseDto(reportResponseDto.reportedAccount, reportResponseDto.getCategory(), reportResponseDto.post, reportResponseDto.thread, account.getEmail());
//  }

//  public Report toEntity(Account account){
//    return Report.builder()
//        .reportedAccount()
//        .reportAccount(account.getEmail())
//        .category()
//        .post()
//        .thread()
//        .build();
//  }

}
