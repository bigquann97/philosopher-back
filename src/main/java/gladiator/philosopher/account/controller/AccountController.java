package gladiator.philosopher.account.controller;

import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignUpRequestDto;
import gladiator.philosopher.account.service.AccountService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

  private final AccountService accountService;

  /**
   * 회원가입
   */
  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
    accountService.signUp(signUpRequestDto);
  }

  /**
   * 로그인
   */
  @PostMapping("/sign-in")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponseDto login(@RequestBody SignInRequestDto signInRequestDto) {
    return accountService.signIn(signInRequestDto);
  }

}
