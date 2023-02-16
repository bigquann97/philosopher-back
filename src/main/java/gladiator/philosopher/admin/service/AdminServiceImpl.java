package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.service.ReportService;
import gladiator.philosopher.thread.service.ThreadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final ReportService reportService;
  private final AccountService accountService;
  private final ThreadService threadService;
  private final AccountRepository accountRepository;

  @Override
  public List<UserInfoByAdminResponseDto> getUsersInfoList() {
    return accountService.selectAccountsInfo();
  }

  @Override
  public List<UserInfoByAdminResponseDto> getAccounts(final AccountSearchCondition condition) {
    return accountRepository.searchAccount(condition);
  }

  @Override
  public List<ReportResponseDto> getReports() {
    return reportService.getReports();
  }

  @Override
  public void modifyUserRole(final Account account) {
    accountService.UpdateAccountRole(account);
  }

  @Override
  public List<ThreadsSimpleResponseDtoByAdmin> getThreadsV2() {
    return threadService.getThreadsV2();
  }
}
