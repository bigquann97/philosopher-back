package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.service.ReportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
  private final AccountRepository accountRepository; // 오늘 이야기 후 경계선 결정할 것.
  private final ReportService reportService;
  @Override
  public List<UserInfoResponseDto> getUsersInfoList() {return accountRepository.getInfoByAccount();}
  @Override
  public List<ReportResponseDto> getReports() {
    return reportService.getReports();
  }

}
