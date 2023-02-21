package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.info.ModifyAccountInfoRequestDto;
import gladiator.philosopher.account.dto.info.ModifyNicknameRequestDto;
import gladiator.philosopher.account.dto.info.ModifyPasswordRequestDto;
import gladiator.philosopher.account.dto.info.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.common.security.AccountDetails;
import java.util.List;

public interface AccountService {

  List<UserInfoByAdminResponseDto> selectAccountsInfo(); // 모든 유저 정보 가지고 오기

  Account getAccount(Long id); // 사용자 가지고 오기 ( 단건조회 )

  void UpdateAccountRole(Account account); // 권한 업데이트 ( 어드민 )

  UserInfoResponseDto getMyInfo(Account account); // 내 정보 가지고 오기



  void modifyAccountImage(Account account, String newUrl); // 프로필 이미지 수정


  String getOldUrl(Account account);

  void adminCheck(); // 어드민 체크 -> 해당 로직 통과하게된다면 어드민

  Long modifyAccountInfo(Account account, ModifyAccountInfoRequestDto infoRequestDto);
}
