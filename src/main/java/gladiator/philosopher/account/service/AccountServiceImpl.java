package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.ModifyNicknameRequestDto;
import gladiator.philosopher.account.dto.ModifyPasswordRequestDto;
import gladiator.philosopher.account.dto.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountImage;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.account.enums.UserRole;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.exception.dto.ExceptionStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


  @Override
  @Transactional(readOnly = true)
  public List<UserInfoByAdminResponseDto> selectAccountsInfo() {
    return accountRepository.getInfoByAccount();
  }

  @Override
  public void AdminCheck() {
  }

  /**
   * 유저 식별자로 유저 객체 찾기 id -> entity
   *
   * @param id
   * @return
   */
  @Override
  public Account getAccount(final Long id) {
    return accountRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND_ACCOUNT));
  }

  @Transactional
  public void UpdateAccountRole(final Account account) {
    if (account.getRole() == (UserRole.ROLE_USER)) {
      account.updateAccountRole(UserRole.ROLE_MANAGER);
    } else {
      account.updateAccountRole(UserRole.ROLE_USER);
    }
  }

  @Override
  public UserInfoResponseDto getMyInfo(final Account account) {
    return new UserInfoResponseDto(account);
  }

  @Override
  @Transactional
  public void modifyMyNickname(
      final Account account,
      final ModifyNicknameRequestDto modifynicknameRequestDto
  ) {
    log.info("account name is : " + account.getNickname());
    account.updateNickname(modifynicknameRequestDto.getNickname());
    accountRepository.saveAndFlush(account);
    log.info("new account name is : " + account.getNickname());
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
  @Transactional
  public void modifyMyPassword(final Account account,
      final ModifyPasswordRequestDto modifyPasswordRequestDto
  ) {
    log.info("my password is : " + account.getPassword());
    String password = passwordEncoder.encode(modifyPasswordRequestDto.getPassword());
    account.updatePassword(password);
    accountRepository.saveAndFlush(account);
    log.info("new password is : " + account.getPassword());
  }

  @Override
  public String getOldUrl(Account account) {
    AccountImage accountImage = accountInfoRepository.getAccountInfoByAccountId(account.getId());
    return accountImage.getImageUrl();
  }

}
