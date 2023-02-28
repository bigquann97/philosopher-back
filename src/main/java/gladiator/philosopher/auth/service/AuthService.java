package gladiator.philosopher.auth.service;

import gladiator.philosopher.account.dto.ReissueResponseDto;
import gladiator.philosopher.account.dto.login.SignInRequestDto;
import gladiator.philosopher.account.dto.login.SignInResponseDto;
import gladiator.philosopher.account.dto.login.SignUpRequestDto;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  void signUp(final SignUpRequestDto signUpRequestDto);

  SignInResponseDto signIn(
      final SignInRequestDto signInRequestDto,
      final HttpServletResponse response
  );

  ReissueResponseDto reissue(
      final TokenRequestDto tokenRequestDto,
      final HttpServletResponse response
  );

  void signOut(final TokenRequestDto dto);

  void sendVerificationMail(final String email);

  void verifyMail(final String email, final String code);

  void checkIfUserNicknameDuplicated(final String nickName);

  void sendFindPasswordMail(final String email);

  void verifyFindPasswordMail(final String email, final String code);
}
