package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.service.ReportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
  private final ReportService reportService;
  private final AccountService accountService;
  @Override
  public List<UserInfoResponseDto> getUsersInfoList() {
    return accountService.selectAccountsInfo();}
  @Override
  public List<ReportResponseDto> getReports() {
    return reportService.getReports();
  }

  @Override
  public void modifyUserRole(Account account) {
    accountService.UpdateAccountRole(account);
  }

}
