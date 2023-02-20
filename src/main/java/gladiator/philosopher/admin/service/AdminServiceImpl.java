package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.dto.post.PostReportResponseDto;
import gladiator.philosopher.report.service.ReportService;
import gladiator.philosopher.thread.service.ThreadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final ReportService reportService;
  private final AccountService accountService;
  private final ThreadService threadService;
  private final AccountRepository accountRepository;

  @Override
  public MyPage<UserInfoByAdminResponseDto> getAccounts(final AccountSearchCondition condition, Pageable pageable) {
    return accountRepository.searchAccount(condition, pageable);
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

  @Override
  public List<PostReportResponseDto> getPostsReports() {
    return reportService.getPostReports();
  }
}
