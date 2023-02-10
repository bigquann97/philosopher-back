package gladiator.philosopher.report.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import java.util.List;

public interface ReportService {

  void reportPost(Long id, ReportRequestDto dto, Account member);

  void reportComment(Long id, ReportRequestDto dto, Account member);

  void reportThread(Long id, ReportRequestDto dto, Account member);

  void reportPostTest(Long id);
  void reportPostTest2(Long id, ReportRequestDto dto, Account member);

  List<ReportResponseDto> getReports();

}
