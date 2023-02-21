package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.admin.dto.ModifyThreadRequestDto;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSearchCondByAdmin;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AdminService {

  void modifyUserRole(Account account); // 권한 수정

  MyPage<UserInfoByAdminResponseDto> getAccounts(AccountSearchCondition condition,
      Pageable pageable);

  List<PostReportResponseDto> getPostsReports();

  MyPage<ThreadSimpleResponseDto> selectArchivedThreads(final ThreadSearchCond of);

  MyPage<ThreadsSimpleResponseDtoByAdmin> searchByThreadsAdmin(final ThreadSearchCondByAdmin cond,
      Pageable pageable);

  Long modifyThread(Long id, ModifyThreadRequestDto threadRequestDto);


}
