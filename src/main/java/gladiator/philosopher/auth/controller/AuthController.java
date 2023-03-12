package gladiator.philosopher.auth.controller;

import gladiator.philosopher.account.dto.ReissueResponseDto;
import gladiator.philosopher.account.dto.login.SignInRequestDto;
import gladiator.philosopher.account.dto.login.SignInResponseDto;
import gladiator.philosopher.account.dto.login.SignUpRequestDto;
import gladiator.philosopher.auth.service.AuthService;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(final @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
    authService.signUp(signUpRequestDto);
  }

  @PostMapping("/sign-in")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponseDto login(
      final @RequestBody SignInRequestDto signInRequestDto,
      final HttpServletResponse response
  ) {
    return authService.signIn(signInRequestDto, response);
  }

  @DeleteMapping("/sign-out")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void signOut(final @RequestBody TokenRequestDto dto) {
    authService.signOut(dto);
  }

  @PostMapping("/re-issue")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ReissueResponseDto reissue(
      final @RequestBody TokenRequestDto tokenRequestDto,
      final HttpServletResponse response
  ) {
    return authService.reissue(tokenRequestDto, response);
  }

  @PostMapping("/mail")
  @ResponseStatus(HttpStatus.OK)
  public void verify(final @RequestParam String email) {
    authService.sendVerificationMail(email);
  }

  @PostMapping("/mail/{code}")
  @ResponseStatus(HttpStatus.OK)
  public void verifyMail(final @RequestParam String email, final @PathVariable String code) {
    authService.verifyMail(email, code);
  }

  @PostMapping("/find-password")
  @ResponseStatus(HttpStatus.OK)
  public void findPassword(final @RequestParam String email) {
    authService.sendFindPasswordMail(email);
  }

  @PostMapping("/find-password/{code}")
  @ResponseStatus(HttpStatus.OK)
  public void verifyFindPasswordMail(
      final @RequestParam String email,
      final @PathVariable String code
  ) {
    authService.verifyFindPasswordMail(email, code);
  }

}
