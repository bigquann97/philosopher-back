package gladiator.philosopher.auth.service;

import gladiator.philosopher.account.dto.login.SignInRequestDto;
import gladiator.philosopher.account.dto.login.SignInResponseDto;
import gladiator.philosopher.account.dto.login.SignUpRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountInfo;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.jwt.JwtTokenProvider;
import gladiator.philosopher.common.jwt.TokenDto;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import gladiator.philosopher.common.util.RedisUtil;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final AccountRepository accountRepository;
  private final AccountInfoRepository accountInfoRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final EmailService emailService;
  private final RedisUtil redisUtil;

  /**
   * 회원가입
   *
   * @param registerRequestDto
   */
  @Transactional
  @Override
  public void signUp(
      final String imageUrl,
      final SignUpRequestDto registerRequestDto
  ) {
    checkByUserEmailDuplicated(registerRequestDto.getEmail());
    checkByUserNickNameDuplicated(registerRequestDto.getNickname());
    Account account = registerRequestDto.toEntity(
        passwordEncoder.encode(registerRequestDto.getPassword()));
    accountRepository.save(account);
    AccountInfo accountInfo = new AccountInfo(account, imageUrl);
    accountInfoRepository.save(accountInfo);
  }

  /**
   * 로그인
   *
   * @param signInRequestDto
   * @param response
   * @return
   */
  @Override
  @Transactional
  public SignInResponseDto signIn(
      final SignInRequestDto signInRequestDto,
      final HttpServletResponse response
  ) {
    Account account = findAccountByEmail(signInRequestDto.getEmail());
    checkByMemberPassword(signInRequestDto.getPassword(), account);
    Authentication authentication = jwtTokenProvider.createAuthentication(
        signInRequestDto.getEmail());
    TokenDto tokenDto = jwtTokenProvider.createTokenDto(authentication);
    redisUtil.setData(authentication.getName(), tokenDto.getRefreshToken());
    response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    return SignInResponseDto.of(account.getEmail(), tokenDto.getAccessToken(),
        tokenDto.getRefreshToken());
  }


  @Override
  @Transactional
  public SignInResponseDto reissue(
      final TokenRequestDto tokenRequestDto,
      final HttpServletResponse response
  ) {
    String email = jwtTokenProvider.getUserInfoFromToken(tokenRequestDto.getAccessToken())
        .getSubject();
    validateRefreshToken(tokenRequestDto);
    Authentication authentication = jwtTokenProvider.createAuthentication(email);
    String validRefreshToken = redisUtil.getData(authentication.getName());

    if (validRefreshToken != null) {
      validateRefreshTokenOwner(validRefreshToken, tokenRequestDto);
      TokenDto tokenDto = jwtTokenProvider.createTokenDto(authentication);
      redisUtil.setData(authentication.getName(), tokenDto.getRefreshToken());
      response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
      return SignInResponseDto.of(email, tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    } else {
      throw new IllegalArgumentException("redis DB에 토큰정보 없음");
    }
  }

  @Override
  @Transactional
  public void signOut(final Account account) {
    String email = account.getEmail();
    redisUtil.deleteData(email);
  }

  @Override
  public void sendVerificationMail(final String email) {
    emailService.sendVerificationMail(email);
  }

  @Override
  public void verifyEmail(final String email, final String code) {
    emailService.verifyEmail(email, code);
  }

  // ===== 내부 메서드 =====

  /**
   * 유저 이메일로 유저 객체 찾기 email -> entity
   *
   * @param email
   * @return
   */
  private Account findAccountByEmail(final String email) {
    return accountRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("사용자 없습니다"));
  }


  private void validateRefreshToken(final TokenRequestDto tokenRequestDto) {
    if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
      throw new IllegalArgumentException("유효하지 않은 리프레시 토큰");
    }
  }

  private void validateRefreshTokenOwner(
      final String validRefreshToken,
      final TokenRequestDto tokenRequestDto
  ) {
    if (!tokenRequestDto.validateToken(validRefreshToken)) {
      throw new IllegalArgumentException("유효하지않은 토큰");
    }
  }

  /**
   * 로그인 시 비밀번호 확인
   *
   * @param password
   * @param account
   */
  private void checkByMemberPassword(final String password, final Account account) {
    if (!passwordEncoder.matches(password, account.getPassword())) {
      throw new CustomException(ExceptionStatus.NOT_MATCH_INFORMATION);
    }
  }

  /**
   * 회원가입 시 유저이메일 중복 확인
   *
   * @param email
   * @throws RuntimeException
   */
  private void checkByUserEmailDuplicated(final String email) {
    if (accountRepository.existsByEmail(email)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_IS_EXIST);
    }
  }

  /**
   * 회원가입 시 유저 닉네임 중복 확인
   *
   * @param nickName
   */
  private void checkByUserNickNameDuplicated(final String nickName) {
    if (accountRepository.existsByNickname(nickName)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_NICKNAME_IS_EXIST);
    }
  }

}
