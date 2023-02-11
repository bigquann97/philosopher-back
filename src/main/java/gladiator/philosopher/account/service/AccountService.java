package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignUpRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.jwt.TokenRequestDto;

public interface AccountService {

  void signUp(SignUpRequestDto signUpRequestDto); // 회원가입

  SignInResponseDto signIn(SignInRequestDto signInRequestDto); // 로그인

  Account findAccountByEmail(String email); // 사용자 id를 이용한 사용자 정보 찾기

  SignInResponseDto reissue(TokenRequestDto tokenRequestDto);

  void signOut(Account account);
}
