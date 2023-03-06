package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.account.dto.RecommendCommentResponseDto;
import gladiator.philosopher.account.dto.SimpleResponseDtoByThread;
import gladiator.philosopher.account.dto.info.ModifyAccountInfoRequestDto;
import gladiator.philosopher.account.dto.info.ModifyAccountNicknameRequestDto;
import gladiator.philosopher.account.dto.info.ModifyAccountPasswordRequestDto;
import gladiator.philosopher.account.dto.info.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import org.springframework.data.domain.Pageable;

public interface AccountService {

  Account getAccount(final Long id);

  void updateAccountRole(final Account account);

  UserInfoResponseDto getMyInfo(final Account account);

  void modifyAccountImage(
      final Account account,
      final String newUrl
  );

  String getOldUrl(final Account account);

  Long modifyAccountPassword(
      final Account account,
      final String password
  );

  Long modifyAccountNickname(
      final Account account,
      final String nickname
  );

  MyPage<PostSimpleResponseDto> getMyPosts(
      final Long accountId,
      final Pageable pageable
  );

  MyPage<CommentSimpleResponseDto> getMyComments(
      final Long accountId,
      final Pageable pageable
  );

  MyPage<PostSimpleResponseDto> getRecommendPostsByAccount(
      final Long accountId,
      final Pageable pageable
  );

  MyPage<SimpleResponseDtoByThread> getRecommendThreadsByAccount(
      final Long accountId,
      final Pageable pageable
  );

  MyPage<RecommendCommentResponseDto> getRecommendCommentsByAccount(
      final Long accountId,
      final Pageable pageable
  );

  MyPage<UserInfoByAdminResponseDto> searchAccounts(
      final AccountSearchCondition condition,
      final Pageable pageable
  );

}
