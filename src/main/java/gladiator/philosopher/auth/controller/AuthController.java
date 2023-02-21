package gladiator.philosopher.auth.controller;

import gladiator.philosopher.account.dto.ReissueResponseDto;
import gladiator.philosopher.account.dto.login.SignInRequestDto;
import gladiator.philosopher.account.dto.login.SignInResponseDto;
import gladiator.philosopher.account.dto.login.SignUpRequestDto;
import gladiator.philosopher.auth.service.AuthService;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import gladiator.philosopher.common.s3.S3Uploader;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  /**
   * 회원가입
   */
  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(final @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
    authService.signUp(signUpRequestDto);
  }

  /**
   * 로그인
   */
  @PostMapping("/sign-in")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponseDto login(
      final @RequestBody SignInRequestDto signInRequestDto,
      final HttpServletResponse response
  ) {
    return authService.signIn(signInRequestDto, response);
  }

  /**
   * 로그아웃 -> redis 속 리프레시 토큰 삭제
   *
   * @param dto
   */
  @DeleteMapping("/sign-out")
  @ResponseStatus(HttpStatus.OK)
  public void signOut(final @RequestBody TokenRequestDto dto) {
    authService.signOut(dto);
  }

  /**
   * 토큰 재발행
   *
   * @param tokenRequestDto
   * @param response
   * @return
   */
  @PostMapping("/re-issue")
  @ResponseStatus(HttpStatus.OK)
  public ReissueResponseDto reissue(
      final @RequestBody TokenRequestDto tokenRequestDto,
      final HttpServletResponse response
  ) {
    return authService.reissue(tokenRequestDto, response);
  }

  /**
   * 비밀번호 재설정 이메일 전송
   *
   * @param email
   */
  @PostMapping("/mail")
  @ResponseStatus(HttpStatus.OK)
  public void verify(final @RequestParam String email) {
    authService.sendVerificationMail(email);
  }

  /**
   * 인증링크 확인
   *
   * @param email
   * @param code
   */
  @PostMapping("/mail/{code}")
  @ResponseStatus(HttpStatus.OK)
  public void getVerify(final @RequestParam String email, final @PathVariable String code) {
    authService.verifyEmail(email, code);
  }

}
