package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.dto.post.PostReportResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AdminService {

  List<ReportResponseDto> getReports(); // 모든 신고 조회
  void modifyUserRole(Account account); // 권한 수정

  List<ThreadsSimpleResponseDtoByAdmin> getThreadsV2();

  MyPage<UserInfoByAdminResponseDto> getAccounts(AccountSearchCondition condition, Pageable pageable);

  List<PostReportResponseDto> getPostsReports();

  // 모든 게시글 조회

  // 권한 부여하기 ( MASTER - ADMIN - USER)




}
