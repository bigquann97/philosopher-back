package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.accountRequestDto;
import gladiator.philosopher.account.dto.LoginResponseDto;
import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.entity.Account;

public interface AccountService {

  void signUp(accountRequestDto accountRequestDto); // 회원가입

  void checkByUserEmailDuplicated(String email); // 사용자 중복 체크

  void checkByUserNickNameDuplicated(String nickName); // 사용자 닉네임 체크

  LoginResponseDto signIn(SignInRequestDto signInRequestDto); // 로그인

  Account findByAccount(String email); // 사용자 id를 이용한 사용자 정보 찾기

  void checkByMemberPassword(String password, Account account); // 비밀번호 확인

}
