package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.ModifyPasswordRequestDto;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.account.dto.ModifyNicknameRequestDto;
import gladiator.philosopher.account.dto.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
  List<UserInfoByAdminResponseDto> selectAccountsInfo(); // 모든 유저 정보 가지고 오기

  Account getAccount(Long id); // 사용자 가지고 오기 ( 단건조회 )

  void UpdateAccountRole(Account account); // 권한 업데이트 ( 어드민 )

  UserInfoResponseDto getMyInfo(Account account); // 내 정보 가지고 오기

  void modifyMyNickname(Account account, ModifyNicknameRequestDto modifynicknameRequestDto); // 닉네임 변경
  void modifyAccountImage(Account account, String newUrl); // 프로필 이미지 수정

  void modifyMyPassword(Account account, ModifyPasswordRequestDto modifyPasswordRequestDto); // 비밀번호 변경

  String getOldUrl(Account account);

  void AdminCheck(); // 어드민 체크 -> 해당 로직 통과하게된다면 어드민

}
