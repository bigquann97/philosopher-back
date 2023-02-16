package gladiator.philosopher.auth.service;

import gladiator.philosopher.Account.dto.login.SignInRequestDto;
import gladiator.philosopher.Account.dto.login.SignInResponseDto;
import gladiator.philosopher.Account.dto.login.SignUpRequestDto;
import gladiator.philosopher.Account.entity.Account;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  void signUp(String imageUrl, SignUpRequestDto signUpRequestDto); // 회원가입

  SignInResponseDto signIn(SignInRequestDto signInRequestDto, HttpServletResponse response); // 로그인
  SignInResponseDto reissue(TokenRequestDto tokenRequestDto, HttpServletResponse response); // 토큰 재발행

  void signOut(Account account); // 로그아웃

  void sendMail(String to, String sub, String text);
  void sendVerificationMail(String email);
  void verifyEmail(String email, String code);

  Account findAccountByEmail(String email); // 사용자 id를 이용한 사용자 정보 찾기




}
