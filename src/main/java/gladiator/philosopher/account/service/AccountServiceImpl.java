package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignUpRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountImage;
import gladiator.philosopher.account.repository.AccountImageRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.jwt.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final AccountImageRepository accountImageRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * 회원가입
   *
   * @param registerRequestDto
   */
  @Transactional
  @Override
  public void signUp(SignUpRequestDto registerRequestDto) {
    checkByUserEmailDuplicated(registerRequestDto.getEmail());
    checkByUserNickNameDuplicated(registerRequestDto.getNickname());
    AccountImage image = new AccountImage("default_image.jpg");
    String password = passwordEncoder.encode(registerRequestDto.getPassword());
    Account account = registerRequestDto.toEntity(password, image);
    accountImageRepository.save(image);
    accountRepository.save(account);
  }

  /**
   * 로그인
   * @param signInRequestDto
   * @return
   */
  @Override
  @Transactional(readOnly = true)
  public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
    Account account = findAccountByEmail(signInRequestDto.getEmail());
    checkByMemberPassword(signInRequestDto.getPassword(), account);
    String token = jwtTokenProvider.createToken(account.getEmail(), account.getType());
    return SignInResponseDto.of(account, token);
  }

  /**
   * 로그인 시 비밀번호 확인
   *
   * @param password
   * @param account
   */
  private void checkByMemberPassword(String password, Account account) {
    if (!passwordEncoder.matches(password, account.getPassword())) {
      throw new CustomException(ExceptionStatus.NOT_MATCH_INFORMATION);
    }
  }

  /**
   * 유저 아이디로 유저 객체 찾기
   *
   * @param email
   * @return
   */
  public Account findAccountByEmail(String email) {
    Account account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("사용자 없습니다"));
    return account;
  }

  @Override
  public Account getAccount(Long id) {
    return accountRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.ACCOUNT_IS_NOT_EXIST));
  }

  /**
   * 회원가입 시 유저이메일 중복 확인
   *
   * @param email
   * @throws RuntimeException
   */
  private void checkByUserEmailDuplicated(String email) {
    if (accountRepository.existsByEmail(email)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_IS_EXIST);
    }
  }

  /**
   * 회원가입 시 유저 닉네임 중복 확인
   *
   * @param nickName
   */
  private void checkByUserNickNameDuplicated(String nickName) {
    if (accountRepository.existsByNickname(nickName)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_NICKNAME_IS_EXIST);
    }
  }

  @Override
  public List<UserInfoResponseDto> selectAccountsInfo() {
    return accountRepository.getInfoByAccount();
  }

  @Override
  public void AdminCheck() {

  }
  @Transactional
  public void UpdateAccountRole(Account account){
    if(account.getType().equals(UserRole.ROLE_USER))
      account.UpdateAccountRole(UserRole.ROLE_ADMIN);
    else
      account.UpdateAccountRole(UserRole.ROLE_USER);
  }

}
