package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignUpRequestDto;
import gladiator.philosopher.account.entity.Account;

public interface AccountService {

  void signUp(SignUpRequestDto signUpRequestDto); // 회원가입

  SignInResponseDto signIn(SignInRequestDto signInRequestDto); // 로그인

  Account findAccountByEmail(String email); // 사용자 id를 이용한 사용자 정보 찾기


}
