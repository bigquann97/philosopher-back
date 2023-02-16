package gladiator.philosopher.Account.service;

import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.Account.dto.ModifyProfileRequestDto;
import gladiator.philosopher.Account.dto.UserInfoResponseDto;
import gladiator.philosopher.Account.entity.Account;
import gladiator.philosopher.Account.repository.AccountRepository;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountServiceImpl implements AccountService{
  private final AccountRepository accountRepository;

  @Override
  public List<UserInfoByAdminResponseDto> selectAccountsInfo() {
    return accountRepository.getInfoByAccount();
  }

  @Override
  public void AdminCheck() {

  }

  /**
   * 유저 식별자로 유저 객체 찾기 id -> entity
   * @param id
   * @return
   */
  @Override
  public Account getAccount(Long id) {
    return accountRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.ACCOUNT_IS_NOT_EXIST));
  }

  @Transactional
  public void UpdateAccountRole(Account account) {
    if (account.getType().equals(UserRole.ROLE_USER)) {
      account.UpdateAccountRole(UserRole.ROLE_ADMIN);
    } else {
      account.UpdateAccountRole(UserRole.ROLE_USER);
    }
  }

  @Override
  public UserInfoResponseDto getMyInfo(Account account) {
    UserInfoResponseDto MyInfo = new UserInfoResponseDto(account);
    return MyInfo;
  }

  @Override
  @Transactional
  public void modifyInfo(Account account, ModifyProfileRequestDto modifyProfileRequestDto) {

  }
}
