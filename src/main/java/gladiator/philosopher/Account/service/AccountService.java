package gladiator.philosopher.Account.service;

import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.Account.dto.ModifyProfileRequestDto;
import gladiator.philosopher.Account.dto.UserInfoResponseDto;
import gladiator.philosopher.Account.entity.Account;
import java.util.List;

public interface AccountService {
  List<UserInfoByAdminResponseDto> selectAccountsInfo(); // 모든 유저 정보 가지고 오기

  void AdminCheck(); // 어드민 체크 -> 해당 로직 통과하게된다면 어드민

  Account getAccount(Long id); // 사용자 가지고 오기 ( 단건조회 )

  void UpdateAccountRole(Account account); // 권한 업데이트 ( 어드민 )

  UserInfoResponseDto getMyInfo(Account account); // 내 정보 가지고 오기

  void modifyInfo(Account account, ModifyProfileRequestDto modifyProfileRequestDto); // 내 정보 수정

}
