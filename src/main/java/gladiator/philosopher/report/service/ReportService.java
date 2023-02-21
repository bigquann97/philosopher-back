package gladiator.philosopher.report.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.report.dto.ReportRequestDto;
import java.util.List;

public interface ReportService {

  void reportPost(Long id, ReportRequestDto dto, Account member);

  void reportComment(Long id, ReportRequestDto dto, Account member);

  void reportThread(Long id, ReportRequestDto dto, Account member);

  List<PostReportResponseDto> getPostReports();

}
