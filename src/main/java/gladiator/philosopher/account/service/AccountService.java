package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.account.dto.info.ModifyAccountInfoRequestDto;
import gladiator.philosopher.account.dto.info.ModifyAccountNicknameRequestDto;
import gladiator.philosopher.account.dto.info.ModifyAccountPasswordRequestDto;
import gladiator.philosopher.account.dto.info.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.dto.MyPage;
import org.springframework.data.domain.Pageable;

public interface AccountService {

  Account getAccount(Long id);

  void updateAccountRole(Account account);

  UserInfoResponseDto getMyInfo(Account account);

  void modifyAccountImage(Account account, String newUrl);

  String getOldUrl(Account account);

  Long modifyAccountPassword(Account account, String password);

  Long modifyAccountNickname(Account account, String nickname);


  MyPage<PostSimpleResponseDto> getMyPosts(Account account, Pageable pageable);

  MyPage<CommentSimpleResponseDto> getMyComments(Account account, Pageable pageable);
}
