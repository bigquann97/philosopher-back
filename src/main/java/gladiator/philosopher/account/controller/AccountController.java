package gladiator.philosopher.account.controller;

import gladiator.philosopher.account.dto.accountRequestDto;
import gladiator.philosopher.account.dto.LoginResponseDto;
import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.service.AccountService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public HttpStatus signUp(@Valid @RequestBody accountRequestDto accountRequestDto) {
    accountService.signUp(accountRequestDto);
    return HttpStatus.CREATED;
  }

  /**
   * 로그인
   */
  @PostMapping("/sign-in")
  public ResponseEntity<LoginResponseDto> login(@RequestBody SignInRequestDto signInRequestDto) {
    return ResponseEntity.status(200).body(accountService.signIn(signInRequestDto));
  }

}
