package gladiator.philosopher.account.controller;

import gladiator.philosopher.account.dto.SignInRequestDto;
import gladiator.philosopher.account.dto.SignInResponseDto;
import gladiator.philosopher.account.dto.SignUpRequestDto;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import gladiator.philosopher.common.security.AccountDetails;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

  private final AccountService accountService;

  /**
   * 회원가입
   */
  @PostMapping(value = "/sign-up",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
      MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(@RequestPart("image") List<MultipartFile> multipartFiles,
      @Valid @RequestPart("dto") SignUpRequestDto signUpRequestDto) {
    accountService.signUp(multipartFiles, signUpRequestDto);
  }

  /**
   * 로그인
   */
  @PostMapping("/sign-in")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponseDto login(@RequestBody SignInRequestDto signInRequestDto,
      HttpServletResponse response) {
    return accountService.signIn(signInRequestDto, response);
  }

  /**
   * 토큰 재발행
   * @param tokenRequestDto
   * @param response
   * @return
   */
  @PostMapping("/re-issue")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponseDto reissue(@RequestBody TokenRequestDto tokenRequestDto, HttpServletResponse response) {
    return accountService.reissue(tokenRequestDto, response);
  }

  /**
   * 로그아웃 -> redis 속 리프레시 토큰 삭제
   * @param accountDetails
   */
  @DeleteMapping("/sign-out")
  @ResponseStatus(HttpStatus.OK)
  public void signOut(@AuthenticationPrincipal AccountDetails accountDetails) {
    System.out.println(accountDetails.getAccount());
    accountService.signOut(accountDetails.getAccount());
  }

}
