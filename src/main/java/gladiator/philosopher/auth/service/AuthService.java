package gladiator.philosopher.auth.service;

import gladiator.philosopher.account.dto.ReissueResponseDto;
import gladiator.philosopher.account.dto.login.SignInRequestDto;
import gladiator.philosopher.account.dto.login.SignInResponseDto;
import gladiator.philosopher.account.dto.login.SignUpRequestDto;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  void signUp(SignUpRequestDto signUpRequestDto);
  SignInResponseDto signIn(SignInRequestDto signInRequestDto, HttpServletResponse response);
  ReissueResponseDto reissue(final TokenRequestDto tokenRequestDto,
      final HttpServletResponse response);

  void signOut(TokenRequestDto dto);

  void sendVerificationMail(String email);

  void verifyEmail(String email, String code);

}
