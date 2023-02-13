package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignUpRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import java.util.List;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

  void signUp(List<MultipartFile> multipartFiles, SignUpRequestDto signUpRequestDto); // 회원가입

  SignInResponseDto signIn(SignInRequestDto signInRequestDto, HttpServletResponse response); // 로그인

  Account findAccountByEmail(String email); // 사용자 id를 이용한 사용자 정보 찾기


  List<UserInfoResponseDto> selectAccountsInfo(); // 모든 유저 정보 가지고 오기

  void AdminCheck(); // 어드민 체크 -> 해당 로직 통과하게된다면 어드민

  Account getAccount(Long id); // 사용자 가지고 오기 ( 단건조회 )

  void UpdateAccountRole(Account account); // 권한 업데이트 ( 어드민 )

  SignInResponseDto reissue(TokenRequestDto tokenRequestDto, HttpServletResponse response); // 토큰 재발행

  void signOut(Account account); // 로그아웃
}
