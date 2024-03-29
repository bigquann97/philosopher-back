package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.account.dto.RecommendCommentResponseDto;
import gladiator.philosopher.account.dto.SimpleResponseDtoByThread;
import gladiator.philosopher.account.dto.info.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountImage;
import gladiator.philosopher.account.enums.UserRole;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.auth.service.AuthService;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.exception.dto.ExceptionStatus;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.recommend.service.RecommendService;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountInfoRepository accountInfoRepository;
  private final AuthService authService;
  private final CommentService commentService;
  private final PostService postService;
  private final ThreadService threadService;
  private final RecommendService recommendService;

  /**
   * 유저 식별자로 유저 객체 찾기 id -> entity
   *
   * @param id
   * @return
   */
  @Override
  @Transactional(readOnly = true)
  public Account getAccount(final Long id) {
    return accountRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND_ACCOUNT));
  }

  @Transactional
  public void updateAccountRole(final Account account) {
    if (account.getRole() == (UserRole.ROLE_USER)) {
      account.updateAccountRole(UserRole.ROLE_MANAGER);
    } else {
      account.updateAccountRole(UserRole.ROLE_USER);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public UserInfoResponseDto getMyInfo(final Account account) {
    String accountImageUrl = accountInfoRepository.getAccountImageById(account.getId());
    return new UserInfoResponseDto(account, accountImageUrl);
  }


  @Override
  @Transactional
  public void modifyAccountImage(Account account, String newUrl) {
    final AccountImage accountImage = accountInfoRepository.getAccountInfoByAccountId(
        account.getId());
    accountImage.updateImageUrl(newUrl);
    accountInfoRepository.saveAndFlush(accountImage);
  }


  @Override
  @Transactional(readOnly = true)
  public String getOldUrl(Account account) {
    AccountImage accountImage = accountInfoRepository.getAccountInfoByAccountId(account.getId());
    return accountImage.getImageUrl();
  }

  @Override
  @Transactional
  public Long modifyAccountPassword(
      final Account account,
      final String password
  ) {
    String encodePassword = passwordEncoder.encode(password);
    Account resultAccount = account.updatePassword(encodePassword);
    accountRepository.saveAndFlush(resultAccount);
    return resultAccount.getId();
  }

  @Override
  @Transactional
  public Long modifyAccountNickname(
      final Account account,
      final String nickname) {
    authService.checkIfUserNicknameDuplicated(nickname);
    Account resultAccount = account.updateNickname(nickname);
    accountRepository.saveAndFlush(resultAccount);
    return resultAccount.getId();
  }

  @Override
  public MyPage<CommentSimpleResponseDto> getMyComments(
      final Long accountId,
      final Pageable pageable) {
    return commentService.getMyComments(accountId, pageable);
  }

  @Override
  public MyPage<PostSimpleResponseDto> getMyPosts(
      final Long accountId,
      final Pageable pageable) {
    return postService.getMyPosts(accountId, pageable);
  }

  @Override
  public MyPage<PostSimpleResponseDto> getRecommendPostsByAccount(
      final Long accountId,
      final Pageable pageable
  ) {
    return postService.getRecommendPostsByAccount(accountId, pageable);
  }

  @Override
  public MyPage<SimpleResponseDtoByThread> getRecommendThreadsByAccount(
      final Long accountId,
      final Pageable pageable
  ) {
    return threadService.getRecommendThreadsByAccount(accountId, pageable);
  }

  @Override
  public MyPage<RecommendCommentResponseDto> getRecommendCommentsByAccount(
      final Long accountId,
      final Pageable pageable) {
    return recommendService.getRecommendCommentsByAccount(accountId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public MyPage<UserInfoByAdminResponseDto> searchAccounts(AccountSearchCondition condition,
      Pageable pageable) {
    return accountRepository.searchAccount(condition, pageable);
  }

}
