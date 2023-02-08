package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.AccountRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.enumtype.GenderType;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import gladiator.philosopher.security.JwtTokenProvider;
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
   * 회원가입
   *
   * @param registerRequestDto
   */
  @Transactional
  @Override
  public void signUp(AccountRequestDto registerRequestDto) {
    checkByUserEmailDuplicated(registerRequestDto.getEmail());
    checkByUserNickNameDuplicated(registerRequestDto.getNickname());
    String password = passwordEncoder.encode(registerRequestDto.getPassword());
    GenderType gender = registerRequestDto.checkGender(registerRequestDto.getGender());
    Account account = registerRequestDto.toEntity(password, gender);
    accountRepository.save(account);
  }

  /**
   * 회원가입 시 유저이메일 중복 확인
   *
   * @param email
   * @throws RuntimeException
   */
  private void checkByUserEmailDuplicated(String email){
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
    if (accountRepository.existsByNickName(nickName)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_NICKNAME_IS_EXIST);
    }
  }

  /**
   * 로그인
   *
   * @param signInRequestDto
   * @return
   */
  @Override
  @Transactional
  public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
    Account account = findByAccount(signInRequestDto.getEmail());
    checkByMemberPassword(signInRequestDto.getPassword(), account);
    return SignInResponseDto.of(account,
        jwtTokenProvider.createToken(account.getEmail(), account.getType()));
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
  @Override
  public Account findByAccount(String email) {
    Account account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("사용자 없습니다"));
    return account;
  }

}
