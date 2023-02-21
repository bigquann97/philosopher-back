package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.info.ModifyAccountInfoRequestDto;
import gladiator.philosopher.account.dto.info.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;

public interface AccountService {

  Account getAccount(Long id); // 사용자 가지고 오기 ( 단건조회 )

  void UpdateAccountRole(Account account); // 권한 업데이트 ( 어드민 )

  UserInfoResponseDto getMyInfo(Account account); // 내 정보 가지고 오기

  void modifyAccountImage(Account account, String newUrl); // 프로필 이미지 수정

  String getOldUrl(Account account);

  Long modifyAccountInfo(Account account, ModifyAccountInfoRequestDto infoRequestDto);

}
