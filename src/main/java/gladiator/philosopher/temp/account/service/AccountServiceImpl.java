package gladiator.philosopher.temp.account.service;

import gladiator.philosopher.temp.account.dto.accountRequestDto;
import gladiator.philosopher.temp.account.dto.LoginResponseDto;
import gladiator.philosopher.temp.account.dto.SignInRequestDto;
import gladiator.philosopher.temp.account.entity.Account;
import gladiator.philosopher.temp.account.repository.AccountRepository;
import gladiator.philosopher.temp.common.enumtype.GenderType;
import gladiator.philosopher.temp.common.exception.CustomException;
import gladiator.philosopher.temp.common.exception.ExceptionStatus;
import gladiator.philosopher.temp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   *회원가입
   * @param registerRequestDto
   */
  @Transactional
  @Override
  public void signUp(accountRequestDto registerRequestDto) {
    checkByUserEmailDuplicated(registerRequestDto.getEmail());
    checkByUserNickNameDuplicated(registerRequestDto.getNickName());
    String password = passwordEncoder.encode(registerRequestDto.getPassword());
    GenderType gender = registerRequestDto.checkGender(registerRequestDto.getGender());
    Account account = registerRequestDto.toEntity(password, gender);
    accountRepository.save(account);}

  /**
   * 회원가입 시 유저이메일 중복 확인
   * @param email
   * @throws RuntimeException
   */
  @Override
  public void checkByUserEmailDuplicated(String email) throws RuntimeException {
    if (accountRepository.existsByEmail(email)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_IS_EXIST);
    }
  }

  /**
   * 회원가입 시 유저 닉네임 중복 확인
   * @param nickName
   */
  @Override
  public void checkByUserNickNameDuplicated(String nickName) {
    if (accountRepository.existsByNickName(nickName)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_NICKNAME_IS_EXIST);
    }
  }

  /**
   * 로그인
   * @param signInRequestDto
   * @return
   */
  @Override
  @Transactional
  public LoginResponseDto signIn(SignInRequestDto signInRequestDto) {
    Account account = findByAccount(signInRequestDto.getEmail());
    checkByMemberPassword(signInRequestDto.getPassword(), account);
    return LoginResponseDto.of(account, jwtTokenProvider.createToken(account.getEmail(), account.getType()));
  }

  /**
   * 로그인 시 비밀번호 확인
   * @param password
   * @param account
   */
  @Override
  public void checkByMemberPassword(String password, Account account) {
    if (!passwordEncoder.matches(password, account.getPassword())) {
      throw new CustomException(ExceptionStatus.NOT_MATCH_INFORMATION);
    }
  }

  /**
   * 유저 아이디로 유저 객체 찾기
   * @param email
   * @return
   */
  @Override
  public Account findByAccount(String email) {
    Account account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("사용자 없습니다"));
    return account;
  }
}
