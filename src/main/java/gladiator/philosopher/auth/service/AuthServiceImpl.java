package gladiator.philosopher.auth.service;

import static gladiator.philosopher.auth.service.EmailService.VALUE_TRUE;
import static gladiator.philosopher.auth.service.EmailService.WHITELIST_KEY_PREFIX;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.DUPLICATED_ACCOUNT;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.DUPLICATED_NICKNAME;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.INVALID_EMAIL_OR_PW;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.INVALID_REFRESH_TOKEN;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_ACCOUNT;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_VERIFIED_EMAIL;
import static gladiator.philosopher.common.jwt.JwtAuthenticationFilter.BLACK_LIST_KEY_PREFIX;
import static gladiator.philosopher.common.jwt.JwtTokenProvider.ACCESS_TOKEN_EXPIRE_TIME;
import static gladiator.philosopher.common.jwt.JwtTokenProvider.AUTHORIZATION_HEADER;
import static gladiator.philosopher.common.jwt.JwtTokenProvider.BEARER_PREFIX;
import static gladiator.philosopher.common.jwt.JwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME;

import gladiator.philosopher.account.dto.ReissueResponseDto;
import gladiator.philosopher.account.dto.login.SignInRequestDto;
import gladiator.philosopher.account.dto.login.SignInResponseDto;
import gladiator.philosopher.account.dto.login.SignUpRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountImage;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.exception.AuthException;
import gladiator.philosopher.common.exception.DuplicatedException;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.exception.dto.ExceptionStatus;
import gladiator.philosopher.common.jwt.JwtTokenProvider;
import gladiator.philosopher.common.jwt.TokenDto;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import gladiator.philosopher.common.util.RedisUtil;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Component
@Configuration
public class AuthServiceImpl implements AuthService {

  private final AccountRepository accountRepository;
  private final AccountInfoRepository accountInfoRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final EmailService emailService;
  private final RedisUtil redisUtil;

  @Value(value = "${default.image}")
  private String imageUrl;

  /**
   * 회원가입
   *
   * @param signUpRequestDto
   */
  @Transactional
  @Override
  public void signUp(final SignUpRequestDto signUpRequestDto) {
    checkIfUserEmailDuplicated(signUpRequestDto.getEmail());
    checkIfUserNickNameDuplicated(signUpRequestDto.getNickname());
    checkIfEmailVerified(signUpRequestDto.getEmail());
    Account account = signUpRequestDto.toEntity(
        passwordEncoder.encode(signUpRequestDto.getPassword()));
    accountRepository.save(account);
    AccountImage accountImage = new AccountImage(account, imageUrl);
    accountInfoRepository.save(accountImage);

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
    checkIfPasswordIsCorrect(signInRequestDto.getPassword(), account);

    // 토큰 발행
    Authentication authentication = jwtTokenProvider.createAuthentication(
        signInRequestDto.getEmail());
    TokenDto tokenDto = jwtTokenProvider.createTokenDto(authentication);

    // 로그인시 Redis - key:email, value: refreshToken 저장;
    redisUtil.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(),
        REFRESH_TOKEN_EXPIRE_TIME);
    response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + tokenDto.getAccessToken());

    return SignInResponseDto.of(account.getNickname(), tokenDto.getAccessToken(),
        tokenDto.getRefreshToken(), account.getRole());
  }


  @Override
  @Transactional
  public ReissueResponseDto reissue(
      final TokenRequestDto tokenRequestDto,
      final HttpServletResponse response
  ) {
    String email = jwtTokenProvider.getUserInfoFromToken(tokenRequestDto.getAccessToken())
        .getSubject();
    validateRefreshToken(tokenRequestDto);

    try {
      Authentication authentication = jwtTokenProvider.createAuthentication(email);
      String validRefreshToken = redisUtil.getData(email);
      validateRefreshTokenOwner(validRefreshToken, tokenRequestDto);

      // 새로운 토큰 발행
      TokenDto tokenDto = jwtTokenProvider.createTokenDto(authentication);
      redisUtil.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(),
          REFRESH_TOKEN_EXPIRE_TIME);
      response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + tokenDto.getAccessToken());

      return ReissueResponseDto.of(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    } catch (NullPointerException e) {
      throw new AuthException(INVALID_REFRESH_TOKEN);
    }

  }

  @Override
  @Transactional
  public void signOut(final TokenRequestDto dto) {
    if (!jwtTokenProvider.validateToken(dto.getAccessToken())) {
      throw new AuthException(ExceptionStatus.INVALID_ACCESS_TOKEN);
    }

    Claims claim = jwtTokenProvider.getUserInfoFromToken(dto.getAccessToken());
    String email = claim.getSubject();
    redisUtil.deleteData(email);

    redisUtil.setDataExpire(BLACK_LIST_KEY_PREFIX + dto.getAccessToken(),
        VALUE_TRUE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
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
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_ACCOUNT));
  }


  private void validateRefreshToken(final TokenRequestDto tokenRequestDto) {
    if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
      throw new AuthException(INVALID_REFRESH_TOKEN);
    }
  }

  private void validateRefreshTokenOwner(
      final String validRefreshToken,
      final TokenRequestDto tokenRequestDto
  ) {
    if (!tokenRequestDto.validateToken(validRefreshToken)) {
      throw new AuthException(INVALID_REFRESH_TOKEN);
    }
  }

  /**
   * 로그인 시 비밀번호 확인
   *
   * @param password
   * @param account
   */
  private void checkIfPasswordIsCorrect(final String password, final Account account) {
    if (!passwordEncoder.matches(password, account.getPassword())) {
      throw new AuthException(INVALID_EMAIL_OR_PW);
    }
  }

  /**
   * 회원가입 시 유저이메일 중복 확인
   *
   * @param email
   * @throws RuntimeException
   */
  private void checkIfUserEmailDuplicated(final String email) {
    if (accountRepository.existsByEmail(email)) {
      throw new DuplicatedException(DUPLICATED_ACCOUNT);
    }
  }

  /**
   * 회원가입 시 유저 닉네임 중복 확인
   *
   * @param nickName
   */
  private void checkIfUserNickNameDuplicated(final String nickName) {
    if (accountRepository.existsByNickname(nickName)) {
      throw new DuplicatedException(DUPLICATED_NICKNAME);
    }
  }

  private void checkIfEmailVerified(String email) {
    try {
      if (!redisUtil.hasKey(WHITELIST_KEY_PREFIX + email)) {
        throw new AuthException(NOT_VERIFIED_EMAIL);
      }
    } catch (NullPointerException e) {
      throw new AuthException(NOT_VERIFIED_EMAIL);
    }
  }

}
