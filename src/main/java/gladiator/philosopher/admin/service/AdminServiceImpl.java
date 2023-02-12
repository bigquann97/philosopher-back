package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDto;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.service.ReportService;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
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

  @Override
  public List<Thread> getThreads() {
    return threadService.getThreads();
  }

  @Override
  public List<ThreadsSimpleResponseDto> getThreadsV2() {
    return threadService.getThreadsV2();
  }
}
