package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.info.ModifyAccountInfoRequestDto;
import gladiator.philosopher.account.dto.info.UserInfoResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountImage;
import gladiator.philosopher.account.enums.UserRole;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.auth.service.AuthService;
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
  private final AuthService authService;

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
  public void updateAccountRole(final Account account) {
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
  public Long modifyAccountInfo(Account account,
      ModifyAccountInfoRequestDto infoRequestDto) {
    authService.checkIfUserNicknameDuplicated(infoRequestDto.getNickname());
    String password = passwordEncoder.encode(infoRequestDto.getPassword());
    account.modifyAccountInfo(infoRequestDto.getNickname(), password);
    accountRepository.saveAndFlush(account);
    return account.getId();
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
  public String getOldUrl(Account account) {
    AccountImage accountImage = accountInfoRepository.getAccountInfoByAccountId(account.getId());
    return accountImage.getImageUrl();
  }


}
