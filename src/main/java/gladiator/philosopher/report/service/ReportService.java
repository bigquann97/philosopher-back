package gladiator.philosopher.report.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.ReportRequestDto;

public interface ReportService {

  void reportPost(Long id, ReportRequestDto dto, Account member);

  void reportComment(Long id, ReportRequestDto dto, Account member);

  void reportThread(Long id, ReportRequestDto dto, Account member);
  
}
