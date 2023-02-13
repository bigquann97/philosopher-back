package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignUpRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import javax.servlet.http.HttpServletResponse;

public interface AccountService {

  void signUp(SignUpRequestDto signUpRequestDto); // 회원가입

  SignInResponseDto signIn(SignInRequestDto signInRequestDto, HttpServletResponse response); // 로그인

  Account findAccountByEmail(String email); // 사용자 id를 이용한 사용자 정보 찾기

  SignInResponseDto reissue(TokenRequestDto tokenRequestDto, HttpServletResponse response);

  void signOut(Account account);
}
