package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import java.util.List;

public interface AdminService {

  List<UserInfoResponseDto> getUsersInfoList(); // 모든 사용자 정보
  List<ReportResponseDto> getReports(); // 모든 신고 조회
  void modifyUserRole(Account account); // 권한 수정




  // 모든 게시글 조회

  /*
  // 모든 Thread 조회  + 댓글까지?
  // 모든 아카이브 조회 + 댓글까지?
  이거 두개 다 비슷할거 같은데 아카이브 = thead이니까?
   */

  // 권한 부여하기 ( MASTER - ADMIN - USER)


}
